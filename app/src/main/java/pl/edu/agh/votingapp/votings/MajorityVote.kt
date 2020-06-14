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
import pl.edu.agh.votingapp.votings.exceptions.QuorumNotReachedException
import pl.edu.agh.votingapp.votings.exceptions.UserAlreadyVotedException
import pl.edu.agh.votingapp.votings.exceptions.VotingIsNotOpenException
import pl.edu.agh.votingapp.votings.exceptions.WrongVotingCodeException

class MajorityVote(override val db: AppDatabase) : BaseVoting{

    private val votingDao: VotingDAO = db.VotingDAO()
    private val userDao: UserDAO = db.UserDAO()
    private val questionDao: QuestionDAO = db.QuestionDAO()
    private val answersDao: AnswersDAO = db.AnswersDAO()

    override suspend fun getResults(votingId: Long): List<Answer> = withContext(Dispatchers.IO) {
        val answers = answersDao.loadAllAnswers(votingId).toMutableList()
        answers.sortByDescending { it.count }
        answers
    }

    @Throws(QuorumNotReachedException::class)
    override suspend fun getWinner(votingId: Long): List<Answer> = withContext(Dispatchers.IO) {
        val answers = getResults(votingId)
        val sumOfVotes = sumOfAllVotes(answers)

        val quorum = votingDao.getVoting(votingId).quorum
        if (quorum != -1 && quorum > sumOfVotes) {
            throw QuorumNotReachedException()
        }

        val result = mutableListOf<Answer>()
        for (i in 0 until votingDao.getVoting(votingId).winnersNb)
            result.add(answers[i])

        if (result[0].count <= sumOfVotes / 2) {
            mutableListOf()
        } else {
            result
        }
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
        answersDao.loadAllAnswers(votingId).fold(0L) { sum, answer -> sum + answer.count }
    }

    override fun updateAnswerCount(votingId: Long, userName: String, answerId: Long, value: Long) {
        val user = userDao.getUserByName(userName)
        if (user.userCode != votingDao.getVoting(votingId).votingCode) {
            throw WrongVotingCodeException()
        }
        if (user.alreadyVote >= votingDao.getVoting(votingId).winnersNb)
            throw UserAlreadyVotedException()
        userDao.incrementCount(user.userId)
        answersDao.updateCount(answerId, value)
    }

    override fun addUser(user: User) {
        userDao.insert(user)
    }

    override fun addQuestion(question: Question) {
        questionDao.insert(question)
    }

    override fun addAnswer(answer: Answer) {
        answersDao.insert(answer)
    }

    private fun sumOfAllVotes(answers: List<Answer>): Long{
        var sumOfVotes: Long = 0

        for(answer in answers) {
            if(answer.voters!=null)
                sumOfVotes += answer.voters.size
        }
        return sumOfVotes
    }


}