package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.entities.StudyProgram
import edu.hsbo.hsbobackend.repository.StudyProgramRepository
import org.springframework.stereotype.Service
import java.nio.file.NoSuchFileException

@Service
class StudyProgramService(val studyProgramRepository: StudyProgramRepository) {

    /**
     * Ruft eine Liste aller Studiengänge ab.
     * @return Eine Liste aller StudyProgram-Objekte.
     */
    fun getAllStudyPrograms(): List<StudyProgram> {
        return studyProgramRepository.findAll()
    }

    /**
     * Ruft einen bestimmten Studiengang anhand seiner eindeutigen ID ab.
     * @param id Die ID des zu suchenden Studiengangs.
     * @return Das gefundene StudyProgram-Objekt.
     * @throws NoSuchFileException wenn kein Studiengang mit der angegebenen ID gefunden wird.
     */
    fun getStudyProgram(id: String): StudyProgram {
        return studyProgramRepository.findById(id).orElseThrow {
            NoSuchFileException("StudyProgram $id not found")
        }
    }

    /**
     * Ruft einen Studiengang anhand seines Namens ab.
     * @param name Der Name des zu suchenden Studiengangs.
     * @return Das gefundene StudyProgram-Objekt.
     */
    fun getStudyProgramByName(name: String): StudyProgram {
        return studyProgramRepository.findByName(name)
    }

    /**
     * Fügt einen neuen Studiengang hinzu oder aktualisiert einen bestehenden.
     * @param studyProgram Der zu speichernde Studiengang.
     * @return Der gespeicherte Studiengang.
     */
    fun addStudyProgram(studyProgram: StudyProgram): StudyProgram {
        return studyProgramRepository.save(studyProgram)
    }
}