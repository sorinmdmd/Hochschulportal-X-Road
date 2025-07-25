package edu.hsbo.akafoeHousing.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api")
class HousingUnitLogController {

    // WARNING: Hardcoding passwords is a major security risk.
    // For production, use environment variables, Spring Cloud Config, or a secrets management solution.
    private val PGPASSWORD = "O1JgsSNqlBCBm2nEQ1iTZQGCdR-8Mhpp"
    private val DOCKER_CONTAINER_NAME = "ss1"
    private val PSQL_HOST = "127.0.0.1"
    private val PSQL_USER = "messagelog"
    private val PSQL_DB = "messagelog"

    // PostgreSQL query to fetch log records, formatted for CSV output.
    // Newlines and carriage returns within the 'message' column are replaced with spaces
    // to ensure proper CSV line parsing.
    // The E'\r' in url_path is retained as it was in your desired output structure.
    private val PSQL_COMMAND_RAW = """
        COPY (
            SELECT
                to_timestamp(time/1000) AS log_time,
                queryid,
                SPLIT_PART(REPLACE(REPLACE(message, CHR(13), ' '), CHR(10), ' '), ' ', 1) AS http_method,
                SPLIT_PART(SPLIT_PART(REPLACE(REPLACE(message, CHR(13), ' '), CHR(10), ' '), ' ', 2), E'\r', 1) AS url_path,
                REPLACE(REPLACE(message, CHR(13), ' '), CHR(10), ' ') AS message_flattened
            FROM messagelog.logrecord
            WHERE discriminator = 'm'
              AND response = false
              AND message LIKE '%studentId%'
            ORDER BY time DESC
        ) TO STDOUT WITH (FORMAT CSV, HEADER TRUE)
    """.trimIndent()

    @GetMapping("/getHousingLogs/{studentId}")
    fun executePsqlQuery(@PathVariable studentId: String ): Any { // Changed return type to Any to allow List<Map<String, String>> or String
        val output = StringBuilder()
        val errorOutput = StringBuilder()

        try {
            // Escape single quotes within the SQL command for bash single-quoted string.
            // This replaces ' with '\'' (close single quote, escaped single quote, open single quote)
            // It also flattens the string to a single line, which is generally safer for -c arguments.
            val escapedPsqlCommandForBashSingleQuote = PSQL_COMMAND_RAW
                .replace("'", "'\\''") // Crucial for embedding in bash single quotes
                .replace("\n", " ")
                .replace("studentId", studentId)
            
            // Construct the full command to be executed within the Docker container.
            // PGPASSWORD is set as an environment variable for psql.
            // The entire 'psql -c ...' part is enclosed in single quotes for bash -c,
            // with the SQL command itself properly escaped for this single-quoted context.
            val bashCommandArg = "export PGPASSWORD='$PGPASSWORD'; psql -h $PSQL_HOST -U $PSQL_USER -d $PSQL_DB -c '${escapedPsqlCommandForBashSingleQuote}'"

            val command = arrayOf(
                "docker", "exec", DOCKER_CONTAINER_NAME,
                "bash", "-c",
                bashCommandArg // Pass the entire constructed string as the argument to bash -c
            )

            val process = ProcessBuilder(*command)
                .redirectErrorStream(true) // Merge stderr into stdout
                .start()

            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }
            }

            val exited = process.waitFor(60, TimeUnit.SECONDS)

            if (!exited) {
                process.destroyForcibly()
                return "Error: Command timed out after 60 seconds.\nOutput:\n$output\nError:\n$errorOutput"
            }

            val exitCode = process.exitValue()

            if (exitCode == 0) {
                // Parse CSV output into a list of maps
                val csvLines = output.toString().trim().split("\n")
                if (csvLines.size < 2) { // Expect at least a header and one data row
                    return "Error: No data or invalid CSV output received.\nOutput:\n$output"
                }

                val headers = csvLines[0].split(",").map { it.trim() } // Split header by comma
                val dataRows = csvLines.drop(1) // Drop header line

                val resultList = mutableListOf<Map<String, String>>()
                for (row in dataRows) {
                    // Use a regex to split by comma, but not if the comma is inside double quotes.
                    // This is a common pattern for robust CSV parsing.
                    val values = row.split(Regex(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                        .map { it.trim().removePrefix("\"").removeSuffix("\"") } // Remove quotes if present

                    if (values.size == headers.size) {
                        val rowMap = headers.zip(values).toMap()
                        resultList.add(rowMap)
                    } else {
                        // Log a warning for malformed rows instead of crashing
                        System.err.println("Warning: Skipping malformed row (column count mismatch): $row")
                    }
                }
                return resultList // Spring will automatically convert this List of Maps to JSON
            } else {
                return "Error: Command exited with code $exitCode.\nOutput:\n$output\nError:\n$errorOutput"
            }

        } catch (e: Exception) {
            return "An unexpected error occurred: ${e.message}\nOutput:\n$output\nError:\n$errorOutput"
        }
    }
}