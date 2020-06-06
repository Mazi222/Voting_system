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
import java.sql.Date


@RunWith(AndroidJUnit4::class)
class SingleNonTransferableVoteGetTest {
    private val TAG: String = "SINGLE_NON_TRANSFERABLE_VOTE"

    private lateinit var db: AppDatabase
    private lateinit var votingDao: VotingDAO
    private lateinit var userDao: UserDAO
    private lateinit var questionDao: QuestionDAO
    private lateinit var answersDao: AnswersDAO

    private lateinit var baseVoting: BaseVoting

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        votingDao = db.VotingDAO()
        userDao = db.UserDAO()
        questionDao = db.QuestionDAO()
        answersDao = db.AnswersDAO()
        mockData()

        baseVoting = SingleNonTransferableVote(db)
    }

    fun mockData(){
        val voting = Voting(votingId = 1, type = VotingType.SINGLE_NON_TRANSFERABLE_VOTE, endTime = Date(1), votingContent = "Test", isOpen = true, winnersNb = 2)
        val voting2 = Voting(votingId = 2, type = VotingType.SINGLE_NON_TRANSFERABLE_VOTE, endTime = Date(1), votingContent = "Test", isOpen = false)
        votingDao.insert(voting)
        votingDao.insert(voting2)
        Log.d(TAG+"_MOCK_DATA", votingDao.getVoting(1).toString())

        val user1 = User(userId = 1, votingId = 1, userName = "TestU1", userCode = 12)
        val user2 = User(userId = 2, votingId = 1, userName = "TestU2", userCode = 13)
        val user3 = User(userId = 3, votingId = 1, userName = "TestU3", userCode = 14)
        val user4 = User(userId = 4, votingId = 1, userName = "TestU4", userCode = 15)
        val user5 = User(userId = 5, votingId = 1, userName = "TestU5", userCode = 15)
        val user6 = User(userId = 6, votingId = 1, userName = "TestU6", userCode = 15)
        userDao.insert(user1)
        userDao.insert(user2)
        userDao.insert(user3)
        userDao.insert(user4)
        userDao.insert(user5)
        userDao.insert(user6)
        Log.d(TAG+"_MOCK_DATA", userDao.loadAllUsers(1).toString())

        val question = Question(votingId = 1,questionContent = "Test")
        questionDao.insert(question)
        Log.d(TAG+"_MOCK_DATA", questionDao.loadAllQuestions(1).toString())


        val answer1 = Answers(answerId = 1, votingId = 1, questionId = question.questionId, answerContent = "Answer1")
        val answer2 = Answers(answerId = 2, votingId = 1, questionId = question.questionId, answerContent = "Answer2")
        val answer3 = Answers(answerId = 3, votingId = 1, questionId = question.questionId, answerContent = "Answer3")
        answersDao.insert(answer1)
        answersDao.insert(answer2)
        answersDao.insert(answer3)
        Log.d(TAG+"_MOCK_DATA", answersDao.loadAllAnswers(1).toString())
        Log.d(TAG+"_MOCK_DATA", answer1.toString())

        answersDao.incrementCount(answer1.answerId)
        answersDao.incrementCount(answer1.answerId)
        answersDao.incrementCount(answer1.answerId)
        answersDao.incrementCount(answer1.answerId)
        answersDao.addVoter(answer1.answerId, user1.userId)
        answersDao.addVoter(answer1.answerId, user5.userId)
        answersDao.addVoter(answer1.answerId, user6.userId)
        answersDao.addVoter(answer1.answerId, user3.userId)
        answersDao.incrementCount(answer2.answerId)
        answersDao.addVoter(answer2.answerId, user2.userId)
        answersDao.incrementCount(answer3.answerId)
        answersDao.addVoter(answer3.answerId, user4.userId)

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
        Log.d(TAG, baseVoting.getWinner(1)[1].toString())
        Assert.assertEquals(answersDao.getAnswer(1), baseVoting.getWinner(1)[0])
        Assert.assertEquals(answersDao.getAnswer(2), baseVoting.getWinner(1)[1])
    }

    @Test
    fun getWinnerTestFail(){
        Log.d(TAG, baseVoting.getWinner(1)[0].toString())
        Assert.assertNotEquals(answersDao.getAnswer(2), baseVoting.getWinner(1)[0])
    }

    @Test(expected = QuorumNotReachedException::class)
    fun getWinnerTestException(){
        val voting = Voting(votingId = 3, type = VotingType.MAJORITY_VOTE, endTime = Date(1), quorum = 10,votingContent = "Test", isOpen = true)
        votingDao.insert(voting)
        baseVoting.getWinner(3)
    }

    @Test
    fun getQuestionTest(){
        Assert.assertEquals(questionDao.getQuestion(1),baseVoting.getQuestion(1)[0])
    }
}