package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import pl.edu.agh.votingapp.VotingType
import java.sql.Date
import java.sql.Time

@Entity
data class Voting (
    @PrimaryKey(autoGenerate = true)
    val votingId: Long = 0,
    @ColumnInfo(name ="type")
    val type: VotingType,
    @ColumnInfo(name ="endTime")
    val endTime: Date,
    @ColumnInfo(name ="votingContent")
    val votingContent: String,
    @ColumnInfo(name ="isOpen")
    val isOpen: Boolean
)