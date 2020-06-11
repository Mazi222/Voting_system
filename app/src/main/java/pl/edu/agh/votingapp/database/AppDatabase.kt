package pl.edu.agh.votingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.edu.agh.votingapp.database.converters.Converters
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.UserDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.database.entities.Voting

@Database(entities = [(Voting::class), (User::class), (Question::class), (Answers::class)], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun VotingDAO(): VotingDAO
    abstract fun UserDAO(): UserDAO
    abstract fun QuestionDAO(): QuestionDAO
    abstract fun AnswersDAO(): AnswersDAO
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "votings.db").build()
                }
            }
            return INSTANCE !!
        }

        fun getInstance() : AppDatabase {
            if(INSTANCE == null)
                throw RuntimeException("Database should be initialized at this point. Use getInstance(context : Context) first ")
            return INSTANCE !!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}