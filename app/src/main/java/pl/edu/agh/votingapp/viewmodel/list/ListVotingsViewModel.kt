package pl.edu.agh.votingapp.viewmodel.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Voting

class ListVotingsViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "ListVotingsViewModel"

    private val db: AppDatabase = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "app-database"
    ).build()

    private val votingDao: VotingDAO = db.VotingDAO()

    val votingList: LiveData<List<Voting>>

    init {
        votingList = votingDao.loadAllVotings()
    }
}