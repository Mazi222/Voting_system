package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Question

@Dao
interface QuestionDAO {

    @Query("SELECT * FROM question WHERE votingId = :vid")
    fun loadAllQuestions(vid: Long): List<Question>

    @Query("SELECT * FROM question WHERE questionId = :qid AND votingId = :vid")
    fun getQuestion(qid: Long, vid: Long): Question

    @Insert
    fun insertAll(questions: List<Question>)

    @Insert
    fun insert(question: Question)

    @Delete
    fun delete(question: Question)

}