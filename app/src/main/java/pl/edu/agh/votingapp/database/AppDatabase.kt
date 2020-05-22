package pl.edu.agh.votingapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.UserDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.database.entities.Voting

@Database(entities = [(Voting::class), (User::class), (Question::class), (Answers::class)], version = 1, exportSchema = false)
abstract class VotingDatabase : RoomDatabase() {
    abstract fun VotingDAO(): VotingDAO
    abstract fun UserDAO(): UserDAO
    abstract fun QuestionDAO(): QuestionDAO
    abstract fun AnswersDAO(): AnswersDAO

    
}