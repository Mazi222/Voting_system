package pl.edu.agh.votingapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.edu.agh.votingapp.database.entities.Voting

@Dao
interface VotingDAO {

    @Query("SELECT * FROM voting")
    fun loadAllVotings(): LiveData<List<Voting>>

    @Query("SELECT * FROM voting WHERE endTime < datetime('now')")
    fun loadFinishedVotings(): LiveData<List<Voting>>

    @Query("SELECT * FROM voting WHERE votingId = :vid")
    fun getVoting(vid: Long): Voting

    @Query("SELECT * FROM voting ORDER BY votingId DESC LIMIT 1")
    fun getWithMaxId(): Voting

    @Insert
    fun insertAll(votings: List<Voting>): List<Long>

    @Insert(entity = Voting::class, onConflict = OnConflictStrategy.ABORT)
    fun insert(voting: Voting): Long

    @Delete
    fun delete(voting: Voting)

}