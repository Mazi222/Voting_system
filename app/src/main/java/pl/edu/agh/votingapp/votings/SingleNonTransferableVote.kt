package pl.edu.agh.votingapp.votings

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.UserDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Question
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.votings.exceptions.QuorumNotReachedException
import pl.edu.agh.votingapp.votings.exceptions.UserAlreadyVotedException
import pl.edu.agh.votingapp.votings.exceptions.VotingIsNotOpenException
import pl.edu.agh.votingapp.votings.exceptions.WrongVotingCodeException

class SingleNonTransferableVote(override val db: AppDatabase) : BaseVoting {

    private val votingDao: VotingDAO = db.VotingDAO()
    private val userDao: UserDAO = db.UserDAO()
    private val questionDao: QuestionDAO = db.QuestionDAO()
    private val answersDao: AnswersDAO = db.AnswersDAO()

    override fun getResults(votingId: Long): List<Answers> {
        val answers = answersDao.loadAllAnswers(votingId).toMutableList()
        answers.sortByDescending { it.count }
        return answers
    }

    @Throws(QuorumNotReachedException::class)
    override fun getWinner(votingId: Long): List<Answers>{
        val answers = getResults(votingId)
        val sumOfVotes = sumOfAllVotes(answers)

        val quorum = votingDao.getVoting(votingId).quorum
        if(quorum != -1 && quorum > sumOfVotes){
            throw QuorumNotReachedException()
        }

        val result = mutableListOf<Answers>()
        for(i in 0 until votingDao.getVoting(votingId).winnersNb) result.add(answers[i])
        return result;
    }

    override fun getQuestion(votingId: Long): List<Question> {
        return questionDao.loadAllQuestions(votingId)
    }

    @Throws(VotingIsNotOpenException::class)
    override fun getVoters(votingId: Long): List<User>{
        if(votingDao.getVoting(votingId).isOpen){
            return userDao.loadAllUsers(votingId)
        }
        throw VotingIsNotOpenException()
    }

    override fun updateAnswerCount(votingId: Long, userName: String, answerId: Long, value: Long) {
        val user = userDao.getUserByName(userName)
        if(user.userCode != votingDao.getVoting(votingId).votingCode) {
            throw WrongVotingCodeException()
        }
        if(user.alreadyVote>=votingDao.getVoting(votingId).winnersNb)
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

    private fun sumOfAllVotes(answers: List<Answers>): Long{
        var sumOfVotes: Long = 0

        for(answer in answers) {
            if(answer.voters!=null)
                sumOfVotes += answer.voters.size
        }
        return sumOfVotes
    }

}