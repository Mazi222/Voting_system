package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Answer

@Dao
interface AnswersDAO {
    @Query("SELECT * FROM answer WHERE votingId = :vid")
    fun loadAllAnswers(vid: Long): List<Answer>

    @Query("SELECT * FROM answer WHERE questionId = :qid")
    fun getAnswersByQuestionId(qid: Long): List<Answer>

    @Query("SELECT * FROM answer WHERE answerId = :aid")
    fun getAnswer(aid: Long): Answer

    @Query("UPDATE answer SET count = count + :value WHERE answerId = :aid")
    fun updateCount(aid: Long, value: Long)

    @Query("UPDATE answer SET count = count + 1 WHERE answerId = :aid")
    fun incrementCount(aid: Long)

    @Query("UPDATE answer SET voters = voters || :newVoter || ',' WHERE answerId = :aid")
    fun addVoter(aid: Long, newVoter: Long)

    @Insert
    fun insertAll(answers: List<Answer>)

    @Insert
    fun insert(answer: Answer): Long

    @Delete
    fun delete(answer: Answer)
}