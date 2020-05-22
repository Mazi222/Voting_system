package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Voting

@Dao
interface VotingDAO {

    @Query("SELECT * FROM answers WHERE votingId = :vid")
    fun loadAllVotings(vid: Int): List<Voting>

    @Query("SELECT * FROM answers WHERE answerId = :aid")
    fun getVoting(aid: Int): Voting

    @Insert
    fun insertAll(votings: List<Voting>)

    @Insert
    fun insert(voting: Voting)

    @Delete
    fun delete(voting: Voting)

}