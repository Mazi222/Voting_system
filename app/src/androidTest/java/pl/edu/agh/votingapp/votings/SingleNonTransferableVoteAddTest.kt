package pl.edu.agh.votingapp.votings

import android.content.Context
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
import pl.edu.agh.votingapp.votings.exceptions.UserAlreadyVotedException
import pl.edu.agh.votingapp.votings.exceptions.WrongVotingCodeException
import java.sql.Date


@RunWith(AndroidJUnit4::class)
class SingleNonTransferableVoteAddTest {
    private val TAG: String = "SINGLE_NON_TRANSFERABLE_VOTE_TEST"

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

        baseVoting = SingleNonTransferableVote(db)
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun addUserTest(){
        val voting: Voting = Voting(votingId = 1, type = VotingType.MAJORITY_VOTE, endTime = Date(1), votingContent = "Test", votingCode = 10, isOpen = true)
        votingDao.insert(voting)

        val user: User = User(userId = 1, votingId = 1, userName = "TEST",userCode = 10)
        val user2: User = User(userId = 2, votingId = 1, userName = "TEST_2",userCode = 10)

        val userList = mutableListOf<User>()
        userList.add(user)
        userList.add(user2)

        baseVoting.addUser(user)
        baseVoting.addUser(user2)

        Assert.assertEquals(userList, userDao.loadAllUsers(1))
        Assert.assertEquals(userList, baseVoting.getVoters(1))
    }

    @Test
    fun addQuestionTest(){
        val question: Question = Question(questionId = 1, votingId = 1, questionContent = "TEST_CONTENT")
        baseVoting.addQuestion(question)
        Assert.assertEquals(question, questionDao.loadAllQuestions(1)[0])
    }

    @Test
    fun addAnswerTest(){
        val answers: Answers = Answers(answerId = 1, votingId = 1, questionId = 1, answerContent = "TEST_ANS_CONTENT")
        baseVoting.addAnswer(answers)
        Assert.assertEquals(answers, answersDao.loadAllAnswers(1)[0])
    }

    @Test
    fun updateAnswerCountTest(){
        val voting: Voting = Voting(votingId = 1, type = VotingType.SINGLE_NON_TRANSFERABLE_VOTE, endTime = Date(1), votingContent = "Test", votingCode = 10, isOpen = true)
        votingDao.insert(voting)

        val user: User = User(userId = 1, votingId = 1, userName = "TEST",userCode = 10)
        baseVoting.addUser(user)

        val question: Question = Question(questionId = 1, votingId = 1, questionContent = "TEST_CONTENT")
        baseVoting.addQuestion(question)

        val answers: Answers = Answers(answerId = 1, votingId = 1, questionId = 1, answerContent = "TEST_ANS_CONTENT")
        baseVoting.addAnswer(answers)

        baseVoting.updateAnswerCount(1, "TEST", 1)
        Assert.assertEquals(1, answersDao.getAnswer(1).count)
    }

    @Test(expected = WrongVotingCodeException::class)
    fun updateAnswerCountWrongCodeTest(){
        val voting: Voting = Voting(votingId = 1, type = VotingType.SINGLE_NON_TRANSFERABLE_VOTE, endTime = Date(1), votingContent = "Test", votingCode = 10, isOpen = true)
        votingDao.insert(voting)

        val user: User = User(userId = 1, votingId = 1, userName = "TEST",userCode = 11)
        baseVoting.addUser(user)

        val question: Question = Question(questionId = 1, votingId = 1, questionContent = "TEST_CONTENT")
        baseVoting.addQuestion(question)

        val answers: Answers = Answers(answerId = 1, votingId = 1, questionId = 1, answerContent = "TEST_ANS_CONTENT")
        baseVoting.addAnswer(answers)

        baseVoting.updateAnswerCount(1, "TEST", 1)
    }

    @Test(expected = UserAlreadyVotedException::class)
    fun updateAnswerCountUserAlreadyVotedTest(){
        val voting: Voting = Voting(votingId = 1, type = VotingType.SINGLE_NON_TRANSFERABLE_VOTE, endTime = Date(1), votingContent = "Test", votingCode = 10, isOpen = true)
        votingDao.insert(voting)

        val user: User = User(userId = 1, votingId = 1, userName = "TEST",userCode = 10)
        baseVoting.addUser(user)

        val question: Question = Question(questionId = 1, votingId = 1, questionContent = "TEST_CONTENT")
        baseVoting.addQuestion(question)

        val answers: Answers = Answers(answerId = 1, votingId = 1, questionId = 1, answerContent = "TEST_ANS_CONTENT")
        baseVoting.addAnswer(answers)

        baseVoting.updateAnswerCount(1, "TEST", 1)
        baseVoting.updateAnswerCount(1, "TEST", 1)
    }

    @Test
    fun updateAnswerCountWrongTest() {
        val voting: Voting = Voting(
            votingId = 1,
            type = VotingType.SINGLE_NON_TRANSFERABLE_VOTE,
            endTime = Date(1),
            votingContent = "Test",
            votingCode = 10,
            isOpen = true
        )
        votingDao.insert(voting)

        val user: User = User(userId = 1, votingId = 1, userName = "TEST", userCode = 10)
        baseVoting.addUser(user)

        val question: Question =
            Question(questionId = 1, votingId = 1, questionContent = "TEST_CONTENT")
        baseVoting.addQuestion(question)

        val answers: Answers =
            Answers(answerId = 1, votingId = 1, questionId = 1, answerContent = "TEST_ANS_CONTENT")
        baseVoting.addAnswer(answers)

        baseVoting.updateAnswerCount(1, "TEST", 1, value = 2)
        Assert.assertNotEquals(1, answersDao.getAnswer(1).count)
    }
}