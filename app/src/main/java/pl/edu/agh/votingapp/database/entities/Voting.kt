package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.edu.agh.votingapp.VotingType
import java.sql.Date

@Entity
data class Voting (
    @PrimaryKey(autoGenerate = true)
    val votingId: Long = 0,
    @ColumnInfo(name ="type")
    var type: VotingType,
    @ColumnInfo(name ="endTime")
    val endTime: Date,
    @ColumnInfo(name ="votingContent")
    val votingContent: String,
    @ColumnInfo(name ="isOpen")
    val isOpen: Boolean
)