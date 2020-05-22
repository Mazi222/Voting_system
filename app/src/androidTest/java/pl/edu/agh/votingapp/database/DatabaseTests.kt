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
    fun addVotingTest() {
        val voting = Voting(votingId = 1, type = VotingType.BORDA_COUNT, endTime = Date(1), votingContent = "Test", isOpen = true)
        votingDao.insert(voting)
        Assert.assertEquals(voting, votingDao.getVoting(voting.votingId))
    }

    @Test
    fun addUserTest(){
        val voting = Voting(votingId = 1, type = VotingType.BORDA_COUNT, endTime = Date(1), votingContent = "Test", isOpen = true)
        val user = User(userId = 1, votingId = voting.votingId, userName = "TestU", userCode = 12)
        votingDao.insert(voting)
        userDao.insert(user)
        Assert.assertEquals(user,userDao.getUser(user.userId,voting.votingId))
    }

    @Test
    fun addQuestionTest(){
        val voting = Voting(votingId = 1, type = VotingType.BORDA_COUNT, endTime = Date(1), votingContent = "Test", isOpen = true)
        val question = Question(questionId = 1, votingId = voting.votingId, questionContent = "TestQ")
        votingDao.insert(voting)
        questionDao.insert(question)
        Assert.assertEquals(question, questionDao.getQuestion(question.questionId, voting.votingId))
    }

    @Test
    fun addAnswerTest(){
        val voting = Voting(votingId = 1, type = VotingType.BORDA_COUNT, endTime = Date(1), votingContent = "Test", isOpen = true)
        val question = Question(questionId = 1, votingId = voting.votingId, questionContent = "TestQ")
        val user = User(userId = 1, votingId = voting.votingId, userName = "TestU", userCode = 12)
        val answer = Answers(answerId = 1, votingId = voting.votingId, questionId = question.questionId, answerContent = "TestA", count = 1)

        votingDao.insert(voting)
        userDao.insert(user)
        questionDao.insert(question)
        answersDao.insert(answer)

        Assert.assertEquals(answer, answersDao.getAnswer(answer.answerId, voting.votingId))
    }
}