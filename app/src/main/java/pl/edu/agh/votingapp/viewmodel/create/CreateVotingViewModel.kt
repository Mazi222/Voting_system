package pl.edu.agh.votingapp.viewmodel.create

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Voting
import java.sql.Date

class CreateVotingViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "CreateVotingViewModel"

    private val db: AppDatabase = AppDatabase.getInstance(application.applicationContext)
    private val votingDao: VotingDAO = db.VotingDAO()
    private val answersDao: AnswersDAO = db.AnswersDAO()
    private val questionDao: QuestionDAO = db.QuestionDAO()

    lateinit var votingType: VotingType
    lateinit var content: String
    lateinit var endTime: Date

    var name = "Voting"
    var quorum: Int = -1
    var numOfPeopleToChoose: Int = -1
    var numOfPeopleEntitled: Int? = Int.MAX_VALUE
    var isOpen: Boolean = true
    var votingCode: Long = -1
    var answers: MutableSet<Answers> = HashSet()

    fun isVotingTypeInitialized() = this::votingType.isInitialized
    fun isContentInitialized() = this::content.isInitialized
    fun isEndTimeInitialized() = this::endTime.isInitialized

    fun createVoting() {
        Log.d(
            TAG,
            String.format(
                "Create voting invoked, type: %s, voting content: %s",
                votingType.type,
                content
            )
        )
        var winnersNb = 1
        if (numOfPeopleToChoose != -1) winnersNb = numOfPeopleToChoose
        val newVoting = Voting(
            type = votingType,
            endTime = endTime,
            votingContent = content,
            quorum = quorum,
            isOpen = isOpen,
            winnersNb = winnersNb
        )
        var votingId: Long

//      wersja bez questions
        AsyncTask.execute {
            votingId = votingDao.insert(newVoting)
            Log.d(TAG, "New voting inserted into database with id: $votingId")
            answers.map { answer ->
                answer.votingId = votingId
                val answerId = answersDao.insert(answer)
                Log.d(
                    TAG,
                    "New answer inserted into database with id $answerId with votingId $votingId"
                )
            }
        }
    }

    fun addAnswer(answer: Answers): Boolean {
        return answers.add(answer)
    }

    fun deleteAnswer(answer: Answers): Boolean {
        return answers.remove(answer)
    }

}