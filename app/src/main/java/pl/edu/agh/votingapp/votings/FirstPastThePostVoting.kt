package pl.edu.agh.votingapp.votings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.UserDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.votings.exceptions.*

class FirstPastThePostVoting(override val db: AppDatabase) : BaseVoting {
    private val votingDao: VotingDAO = db.VotingDAO()
    private val userDao: UserDAO = db.UserDAO()
    private val questionDao: QuestionDAO = db.QuestionDAO()
    private val AnswerDao: AnswersDAO = db.AnswersDAO()

    override suspend fun getResults(votingId: Long): List<Answer> = withContext(Dispatchers.IO) {
        val Answer = AnswerDao.loadAllAnswers(votingId).toMutableList()
        Answer.sortByDescending { it.count }
        Answer
    }

    @Throws(QuorumNotReachedException::class)
    override suspend fun getWinner(votingId: Long): List<Answer> = withContext(Dispatchers.IO) {
        val Answer = getResults(votingId)
        val sumOfVotes = sumOfAllVotes(votingId)

        val quorum = votingDao.getVoting(votingId).quorum
        if (quorum != -1 && quorum > sumOfVotes) {
            throw QuorumNotReachedException()
        }

        val result = mutableListOf<Answer>()
        for (i in 0 until votingDao.getVoting(votingId).winnersNb) result.add(Answer[i])
        result
    }

    override suspend fun getQuestion(votingId: Long): List<Question> = withContext(Dispatchers.IO) {
        questionDao.loadAllQuestions(votingId)
    }

    @Throws(VotingIsNotOpenException::class)
    override suspend fun getVoters(votingId: Long): List<User> = withContext(Dispatchers.IO) {
        if (!votingDao.getVoting(votingId).isOpen) {
            throw VotingIsNotOpenException()
        }
        userDao.loadAllUsers(votingId)
    }

    override suspend fun getCount(votingId: Long): Long = withContext(Dispatchers.IO) {
        AnswerDao.loadAllAnswers(votingId).fold(0L) { sum, answer -> sum + answer.count }
    }

    override fun updateAnswerCount(votingId: Long, userName: String, answerId: Long, value: Long) {
        val user = userDao?.getUserByName(userName)
        if (user == null)
            throw WrongUserNameException()
        if (user.userCode != votingDao.getVoting(votingId).votingCode)
            throw WrongVotingCodeException()
        if (user.alreadyVote >= votingDao.getVoting(votingId).winnersNb)
            throw UserAlreadyVotedException()
        userDao.incrementCount(user.userId)
        AnswerDao.updateCount(answerId, value)
        AnswerDao.addVoter(answerId, user.userId)
    }

    override fun addUser(user: User) {
        userDao.insert(user)
    }

    override fun addQuestion(question: Question) {
        questionDao.insert(question)
    }

    override fun addAnswer(answer: Answer) {
        AnswerDao.insert(answer)
    }

    fun sumOfAllVotes(votingId: Long): Long{
        var sumOfVotes: Long = userDao.loadAllUsers(votingId).size.toLong()
        return sumOfVotes
    }
}