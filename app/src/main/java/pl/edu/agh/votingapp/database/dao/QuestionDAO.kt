package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Question

@Dao
interface QuestionDAO {

    @Query("SELECT * FROM question WHERE votingId = :vid")
    fun loadAllQuestions(vid: Int): List<Question>

    @Query("SELECT * FROM question WHERE questionId = :qid")
    fun getQuestion(qid: Int): Question

    @Insert
    fun insertAll(questions: List<Question>)

    @Insert
    fun insert(question: Question)

    @Delete
    fun delete(question: Question)

}