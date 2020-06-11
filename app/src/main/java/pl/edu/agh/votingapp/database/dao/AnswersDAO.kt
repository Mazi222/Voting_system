package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Answers

@Dao
interface AnswersDAO {
    @Query("SELECT * FROM answers WHERE votingId = :vid")
    fun loadAllAnswers(vid: Long): List<Answers>

    @Query("SELECT * FROM answers WHERE questionId = :qid")
    fun getAnswersByQuestionId(qid: Long): List<Answers>

    @Query("SELECT * FROM answers WHERE answerId = :aid")
    fun getAnswer(aid: Long): Answers

    @Query("UPDATE answers SET count = count + :value WHERE answerId = :aid")
    fun updateCount(aid: Long, value: Long)

    @Query("UPDATE answers SET count = count + 1 WHERE answerId = :aid")
    fun incrementCount(aid: Long)

    @Query("UPDATE answers SET voters = voters || :newVoter || ',' WHERE answerId = :aid")
    fun addVoter(aid: Long, newVoter: Long)

    @Insert
    fun insertAll(answers: List<Answers>)

    @Insert
    fun insert(answer: Answers): Long

    @Delete
    fun delete(answer: Answers)
}