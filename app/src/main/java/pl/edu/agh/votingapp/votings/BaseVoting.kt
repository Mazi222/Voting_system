package pl.edu.agh.votingapp.votings

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User

interface BaseVoting {
    val db: AppDatabase
    fun getResults(votingId: Long): List<Answers>
    fun getWinner(votingId: Long): List<Answers>
    fun getQuestion(votingId: Long): List<Question>
    fun getVoters(votingId: Long): List<User>

    fun updateAnswerCount(votingId: Long, userName: String, answerId: Long, value: Long = 1)
    fun addUser(user: User)
    fun addQuestion(question: Question)
    fun addAnswer(answers: Answers)
//    fun getAllVotings(): List<Voting>{
//        return db.VotingDAO().loadAllVotings()
//    }
}