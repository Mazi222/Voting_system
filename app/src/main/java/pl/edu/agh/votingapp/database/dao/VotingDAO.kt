package pl.edu.agh.votingapp.database.dao

import androidx.room.*
import pl.edu.agh.votingapp.database.entities.Voting

@Dao
interface VotingDAO {

    @Query("SELECT * FROM voting")
    fun loadAllVotings(): List<Voting>

    @Query("SELECT * FROM voting WHERE votingId = :vid")
    fun getVoting(vid: Long): Voting

    @Insert
    fun insertAll(votings: List<Voting>)

    @Insert(entity = Voting::class, onConflict = OnConflictStrategy.ABORT)
    fun insert(voting: Voting)

    @Delete
    fun delete(voting: Voting)

}