package pl.edu.agh.votingapp.votings

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
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.UserDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.database.entities.Voting
import pl.edu.agh.votingapp.votings.exceptions.QuorumNotReachedException
import pl.edu.agh.votingapp.votings.exceptions.VotingIsNotOpenException
import java.util.*


@RunWith(AndroidJUnit4::class)
class FirstPastThePostGetTest {
    private val TAG: String = "FIRST_PAST_THE_POST_VOTE_TEST"

    private lateinit var db: AppDatabase
    private lateinit var votingDao: VotingDAO
    private lateinit var userDao: UserDAO
    private lateinit var questionDao: QuestionDAO
    private lateinit var answersDao: AnswersDAO

    private lateinit var baseVoting: BaseVoting

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        votingDao = db.VotingDAO()
        userDao = db.UserDAO()
        questionDao = db.QuestionDAO()
        answersDao = db.AnswersDAO()

        baseVoting = FirstPastThePostVoting(db)

        mockData()
    }

    fun mockData(){
        val voting = Voting(votingId = 1, type = VotingType.FIRST_PAST_THE_POST, endTime = Date(1), votingContent = "Test", isOpen = true, votingCode = 10, winnersNb = 2)
        val voting2 = Voting(votingId = 2, type = VotingType.FIRST_PAST_THE_POST, endTime = Date(1), votingContent = "Test", isOpen = false, votingCode = 10, winnersNb = 2)
        votingDao.insert(voting)
        votingDao.insert(voting2)
        Log.d(TAG+"_MOCK_DATA", votingDao.getVoting(1).toString())

        val user1 = User(userId = 1, votingId = 1, userName = "TestU1", userCode = 10)
        val user2 = User(userId = 2, votingId = 1, userName = "TestU2", userCode = 10)
        val user3 = User(userId = 3, votingId = 1, userName = "TestU3", userCode = 10)
        val user4 = User(userId = 4, votingId = 1, userName = "TestU4", userCode = 10)
        val user5 = User(userId = 5, votingId = 1, userName = "TestU5", userCode = 10)
        val user6 = User(userId = 6, votingId = 1, userName = "TestU6", userCode = 10)

        baseVoting.addUser(user1)
        baseVoting.addUser(user2)
        baseVoting.addUser(user3)
        baseVoting.addUser(user4)
        baseVoting.addUser(user5)
        baseVoting.addUser(user6)
        Log.d(TAG+"_MOCK_DATA", userDao.loadAllUsers(1).toString())

        val question = Question(votingId = 1,questionContent = "Test")
        baseVoting.addQuestion(question)
        Log.d(TAG+"_MOCK_DATA", questionDao.loadAllQuestions(1).toString())


        val answer1 = Answers(answerId = 1, votingId = 1, questionId = question.questionId, answerContent = "Answer1")
        val answer2 = Answers(answerId = 2, votingId = 1, questionId = question.questionId, answerContent = "Answer2")
        val answer3 = Answers(answerId = 3, votingId = 1, questionId = question.questionId, answerContent = "Answer3")
        baseVoting.addAnswer(answer1)
        baseVoting.addAnswer(answer2)
        baseVoting.addAnswer(answer3)

        Log.d(TAG+"_MOCK_DATA", answersDao.loadAllAnswers(1).toString())
        Log.d(TAG+"_MOCK_DATA", answer1.toString())

        baseVoting.updateAnswerCount(1,"TestU1", 1)
        baseVoting.updateAnswerCount(1,"TestU1", 1)
        baseVoting.updateAnswerCount(1,"TestU2", 1)
        baseVoting.updateAnswerCount(1,"TestU2", 1)
        baseVoting.updateAnswerCount(1,"TestU3", 2)
        baseVoting.updateAnswerCount(1,"TestU4", 3)
        baseVoting.updateAnswerCount(1,"TestU4", 3)
        baseVoting.updateAnswerCount(1,"TestU5", 3)

        Log.d(TAG+"_MOCK_DATA", answersDao.loadAllAnswers(1).toString())
        Log.d(TAG+"_MOCK_DATA", answer1.toString())
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun getAllVotersTest() {
        Assert.assertEquals(userDao.loadAllUsers(1), baseVoting.getVoters(1))
    }

    @Test(expected = VotingIsNotOpenException::class)
    fun getAllVotersClosedVoting(){
        baseVoting.getVoters(2)
    }

    @Test
    fun getSortedResultsTest(){
        val expectedValue = answersDao.loadAllAnswers(1).toMutableList()
        expectedValue.sortByDescending{ it.count }
        Assert.assertEquals(expectedValue, baseVoting.getResults(1))
    }

    @Test
    fun getWinnerTest(){
        Log.d(TAG, baseVoting.getWinner(1)[0].toString())
        Assert.assertEquals(answersDao.getAnswer(1), baseVoting.getWinner(1)[0])
    }

    @Test
    fun getWinnerTestFail(){
        Log.d(TAG, baseVoting.getWinner(1)[0].toString())
        Assert.assertNotEquals(answersDao.getAnswer(2), baseVoting.getWinner(1)[0])
    }

    @Test(expected = QuorumNotReachedException::class)
    fun getWinnerTestException(){
        val voting = Voting(votingId = 3, type = VotingType.FIRST_PAST_THE_POST, endTime = Date(1), quorum = 10,votingContent = "Test", isOpen = true)
        votingDao.insert(voting)
        baseVoting.getWinner(3)
    }

    @Test
    fun getQuestionTest(){
        Assert.assertEquals(questionDao.getQuestion(1),baseVoting.getQuestion(1)[0])
    }
}