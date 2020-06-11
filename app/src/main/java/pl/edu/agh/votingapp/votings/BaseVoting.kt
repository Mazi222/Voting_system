package pl.edu.agh.votingapp.votings

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User

interface BaseVoting {
    val db: AppDatabase
    fun getResults(votingId: Long): List<Answer>
    fun getWinner(votingId: Long): List<Answer>
    fun getQuestions(votingId: Long): List<Question>
    fun getVoters(votingId: Long): List<User>

    fun updateAnswerCount(votingId: Long, userName: String, answerId: Long, value: Long = 1)
    fun addUser(user: User)
    fun addQuestion(question: Question)
    fun addAnswer(answer: Answer)
//    fun getAllVotings(): List<Voting>{
//        return db.VotingDAO().loadAllVotings()
//    }
}