package pl.edu.agh.votingapp.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.database.entities.Voting
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseTests {
    private val TAG: String = "DATABASE_TEST"

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
        val user = User(userId = 1, votingId = 1, userName = "TestU", userCode = 12)
        userDao.insert(user)
        Assert.assertEquals(user,userDao.getUser(user.userId))
    }

    @Test
    fun addQuestionTest(){
        val question = Question(questionId = 1, votingId = 1, questionContent = "TestQ")
        questionDao.insert(question)
        Assert.assertEquals(question, questionDao.getQuestion(question.questionId))
    }

    @Test
    fun addAnswerTest(){
        val answer = Answer(answerId = 1, votingId = 1, questionId = 2, voters = mutableListOf(1), answerContent = "TestA", count = 1)
        answersDao.insert(answer)
        Assert.assertEquals(answer, answersDao.getAnswer(answer.answerId))
    }

    @Test
    fun updateAnswerCountTest(){
        val answer = Answer(answerId = 1, votingId = 1, questionId = 1, voters = mutableListOf(1),answerContent = "TestA", count = 1)
        answersDao.insert(answer)

        answersDao.updateCount(answer.answerId,5)
        Assert.assertNotEquals(answer, answersDao.getAnswer(answer.answerId))

        answer.count+=5
        Log.d(TAG+"updateAnswerCountTest", answer.toString())
        Log.d(TAG+"updateAnswerCountTest", answersDao.getAnswer(answer.answerId).toString())

        Assert.assertEquals(answer, answersDao.getAnswer(answer.answerId))

        answersDao.updateCount(answer.answerId,3)
        answer.count+=3

        Log.d(TAG+"updateAnswerCountTest", answer.toString())
        Log.d(TAG+"updateAnswerCountTest", answersDao.getAnswer(answer.answerId).toString())

        Assert.assertEquals(answer, answersDao.getAnswer(answer.answerId))
    }

    @Test
    fun incrementAnswerCountTest(){
        val answer = Answer(answerId = 1, votingId = 1, questionId = 2, voters = mutableListOf(12),answerContent = "TestA", count = 0)
        answersDao.insert(answer)

        answersDao.incrementCount(answer.answerId)

        Log.d(TAG+"incrementAnswerCountTest", answer.toString())
        Log.d(TAG+"incrementAnswerCountTest", answersDao.getAnswer(answer.answerId).toString())

        Assert.assertEquals(1, answersDao.getAnswer(answer.answerId).count)
    }

    @Test
    fun addVoterTest() {
        val answer = Answer(answerId = 1, votingId = 1, questionId = 1, voters = mutableListOf(1), answerContent = "TestA", count = 1)
        answersDao.insert(answer)

        answersDao.addVoter(answer.answerId,2)
        answersDao.addVoter(answer.answerId,3)

        Assert.assertNotEquals(answer, answersDao.getAnswer(answer.answerId))

        answer.voters?.add(2)
        answer.voters?.add(3)

        Log.d(TAG+"addVoterTest", answer.toString())
        Log.d(TAG+"addVoterTest", answersDao.getAnswer(answer.answerId).toString())

        Assert.assertEquals(answer, answersDao.getAnswer(answer.answerId))
    }

    @Test
    fun addAnswerWithoutVoters(){
        val answer = Answer(answerId = 1, votingId = 1, questionId = 1, voters = mutableListOf(), answerContent = "TestA", count = 1)
        answersDao.insert(answer)

        Log.d(TAG+"addAnswerWithoutVoters", answer.toString())
        Log.d(TAG+"addAnswerWithoutVoters", answersDao.getAnswer(answer.answerId).toString())

        Assert.assertEquals(answer, answersDao.getAnswer(answer.answerId))
    }

}