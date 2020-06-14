package pl.edu.agh.votingapp.votings

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User

interface BaseVoting {
    val db: AppDatabase
    suspend fun getResults(votingId: Long): List<Answer>
    suspend fun getWinner(votingId: Long): List<Answer>
    suspend fun getQuestion(votingId: Long): List<Question>
    suspend fun getVoters(votingId: Long): List<User>
    suspend fun getCount(votingId: Long): Long


    fun updateAnswerCount(votingId: Long, userName: String, answerId: Long, value: Long = 1)
    fun addUser(user: User)
    fun addQuestion(question: Question)
    fun addAnswer(answer: Answer)

}