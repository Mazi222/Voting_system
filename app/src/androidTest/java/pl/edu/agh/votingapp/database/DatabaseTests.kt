package pl.edu.agh.votingapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.UserDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.database.entities.Voting
import java.sql.Date
import java.sql.Time

@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    private lateinit var votingDao: VotingDAO
    private lateinit var userDao: UserDAO
    private lateinit var questionDao: QuestionDAO
    private lateinit var answersDao: AnswersDAO

    private lateinit var db: AppDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        votingDao = db.VotingDAO()
        userDao = db.UserDAO()
        questionDao = db.QuestionDAO()
        answersDao = db.AnswersDAO()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun addVoting() {
//        val voting = Voting(type = VotingType.BORDA_COUNT, endTime = Date(1), votingContent = "Test", isOpen = true)
//        val user = User(votingId = voting.votingId, userName = "TestU", userCode = 12)
//        val question = Question(votingId = voting.votingId, questionContent = "TestQ")
//        val answer = Answers(votingId = voting.votingId, answerOwnerIds = listOf(12), answerContent = "TestAns")
//        voting.users?.add(user)
//        voting.questions?.add(question)
//        voting.answers?.add(answer)
//        db.VotingDAO().insert(voting)
//        Assert.assertEquals(voting, db.VotingDAO().getVoting(0))
    }
}