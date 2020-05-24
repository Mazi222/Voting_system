package pl.edu.agh.votingapp.viewmodel.create

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Voting
import java.sql.Date

class CreateVotingViewModel(application: Application) : AndroidViewModel(application) {

    private val db: AppDatabase = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "app-database"
    ).build()

    private val votingDao: VotingDAO = db.VotingDAO()
    private val answersDao: AnswersDAO = db.AnswersDAO()

    lateinit var votingType: VotingType
    lateinit var content: String
    lateinit var endTime: Date
    var quorum: Int = -1
    var numOfPeopleToChoose: Int = -1
    var numOfPeopleEntitled: Int? = Int.MAX_VALUE
    var isOpen: Boolean = true
    var answers: MutableList<Answers> = arrayListOf()

    fun isVotingTypeInitialized() = this::votingType.isInitialized
    fun isContentInitialized() = this::content.isInitialized
    fun isEndTimeInitialized() = this::endTime.isInitialized

    fun createVoting() {
        val newVoting = Voting(
            type = votingType,
            endTime = endTime,
            votingContent = content,
            quorum = quorum,
            isOpen = isOpen
        )
        var votingId: Long

        AsyncTask.execute {
            votingId = votingDao.insert(newVoting)
            answers.map {
                it.votingId = votingId
            }
            answersDao.insertAll(answers)

            votingDao.loadAllVotings().forEach { println(it) }
            answersDao.loadAllAnswers(votingId).forEach { println(it) }
        }


    }

}