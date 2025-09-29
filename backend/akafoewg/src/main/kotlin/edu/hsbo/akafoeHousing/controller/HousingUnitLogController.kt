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

    // ACHTUNG: Das Hartcodieren von Passwörtern ist ein erhebliches Sicherheitsrisiko.
    // Für den Produktivbetrieb sollten Umgebungsvariablen, Spring Cloud Config oder eine Secrets-Management-Lösung verwendet werden.
    private val PGPASSWORD = "HVjuyWqG0vte9uD4FwkHBJykN1UOdNs1"
    private val DOCKER_CONTAINER_NAME = "ss2"
    private val PSQL_HOST = "127.0.0.1"
    private val PSQL_USER = "messagelog"
    private val PSQL_DB = "messagelog"

    // PostgreSQL-Befehl zum Abrufen von Log-Einträgen, formatiert als CSV-Ausgabe.
    // Zeilenumbrüche und Wagenrückläufe in der 'message'-Spalte werden durch Leerzeichen ersetzt,
    // um ein korrektes Parsen der CSV-Zeilen zu gewährleisten.
    // Das Platzhalter 'studentId' wird später durch die tatsächliche ID ersetzt.
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

    /**
     * Führt eine psql-Abfrage in einem Docker-Container aus, um Log-Daten für eine bestimmte Studenten-ID abzurufen.
     * GET /api/getHousingLogs/{studentId}
     * @param studentId Die ID des Studenten, für den die Logs abgerufen werden sollen.
     * @return Bei Erfolg eine Liste von Objekten (Maps), die die Log-Einträge repräsentieren (wird als JSON zurückgegeben).
     * Bei einem Fehler wird ein String mit der Fehlermeldung zurückgegeben.
     */
    @GetMapping("/getHousingLogs/{studentId}")
    fun executePsqlQuery(@PathVariable studentId: String): Any { // Der Rückgabetyp Any erlaubt die Rückgabe von List<Map<String, String>> oder String
        val output = StringBuilder()
        val errorOutput = StringBuilder()

        try {
            // Escaped das SQL-Kommando für die sichere Einbettung in einen Bash-String, der einfache Anführungszeichen verwendet.
            // Dies ersetzt jedes ' durch '\'', was der Bash mitteilt, das Anführungszeichen als Literal zu behandeln.
            // Außerdem wird der Befehl in eine einzige Zeile umgewandelt, was für -c-Argumente sicherer ist.
            val escapedPsqlCommandForBashSingleQuote = PSQL_COMMAND_RAW
                .replace("'", "'\\''") // Entscheidend für die Einbettung in Bash mit einfachen Anführungszeichen
                .replace("\n", " ")
                .replace("studentId", studentId)

            // Konstruiert den vollständigen Befehl, der im Docker-Container ausgeführt werden soll.
            // PGPASSWORD wird als Umgebungsvariable für psql gesetzt.
            // Der gesamte 'psql -c ...'-Teil wird für 'bash -c' in einfache Anführungszeichen eingeschlossen,
            // wobei das SQL-Kommando selbst für diesen Kontext korrekt escaped wurde.
            val bashCommandArg =
                "export PGPASSWORD='$PGPASSWORD'; psql -h $PSQL_HOST -U $PSQL_USER -d $PSQL_DB -c '${escapedPsqlCommandForBashSingleQuote}'"

            val command = arrayOf(
                "docker", "exec", DOCKER_CONTAINER_NAME,
                "bash", "-c",
                bashCommandArg // Übergibt den gesamten konstruierten String als Argument an 'bash -c'
            )

            // Startet den externen Prozess
            val process = ProcessBuilder(*command)
                .redirectErrorStream(true) // Leitet den Fehlerstream (stderr) in den Standard-Output (stdout) um
                .start()

            // Liest die Ausgabe des Prozesses zeilenweise
            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }
            }

            // Wartet maximal 60 Sekunden auf die Beendigung des Prozesses
            val exited = process.waitFor(60, TimeUnit.SECONDS)

            if (!exited) {
                process.destroyForcibly()
                return "Fehler: Befehl hat das Zeitlimit von 60 Sekunden überschritten.\nAusgabe:\n$output\nFehler:\n$errorOutput"
            }

            val exitCode = process.exitValue()

            if (exitCode == 0) {
                // Parst die CSV-Ausgabe in eine Liste von Maps, wenn der Befehl erfolgreich war
                val csvLines = output.toString().trim().split("\n")
                if (csvLines.size < 2) { // Erwartet mindestens eine Kopfzeile und eine Datenzeile
                    return "Fehler: Keine Daten oder ungültige CSV-Ausgabe empfangen.\nAusgabe:\n$output"
                }

                val headers = csvLines[0].split(",").map { it.trim() } // Teilt die Kopfzeile am Komma
                val dataRows = csvLines.drop(1) // Entfernt die Kopfzeile

                val resultList = mutableListOf<Map<String, String>>()
                for (row in dataRows) {
                    // Verwendet einen Regex, um nach Kommas zu trennen, die nicht innerhalb von Anführungszeichen stehen.
                    // Dies ist ein robustes Verfahren zum Parsen von CSV-Daten.
                    val values = row.split(Regex(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                        .map {
                            it.trim().removePrefix("\"").removeSuffix("\"")
                        } // Entfernt Anführungszeichen, falls vorhanden

                    if (values.size == headers.size) {
                        val rowMap = headers.zip(values).toMap()
                        resultList.add(rowMap)
                    } else {
                        // Gibt eine Warnung für fehlerhafte Zeilen aus, anstatt abzustürzen
                        System.err.println("Warnung: Überspringe fehlerhafte Zeile (Spaltenanzahl stimmt nicht): $row")
                    }
                }
                return resultList // Spring wandelt diese Liste von Maps automatisch in JSON um
            } else {
                return "Fehler: Befehl wurde mit Code $exitCode beendet.\nAusgabe:\n$output\nFehler:\n$errorOutput"
            }

        } catch (e: Exception) {
            return "Ein unerwarteter Fehler ist aufgetreten: ${e.message}\nAusgabe:\n$output\nFehler:\n$errorOutput"
        }
    }
}