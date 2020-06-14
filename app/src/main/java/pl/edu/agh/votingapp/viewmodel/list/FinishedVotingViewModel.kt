package pl.edu.agh.votingapp.viewmodel.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Voting

class FinishedVotingViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "BallotBull"

    private val db: AppDatabase = AppDatabase.getInstance(application.applicationContext)
    private val votingDao: VotingDAO = db.VotingDAO()

    val votingList: LiveData<List<Voting>> = votingDao.loadAllVotings()

    suspend fun getVotingById(votingId: Long): Voting = withContext(Dispatchers.IO) {
        votingDao.getVoting(votingId)
    }

}