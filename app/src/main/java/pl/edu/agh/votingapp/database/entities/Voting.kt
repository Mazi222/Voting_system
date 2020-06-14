package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.edu.agh.votingapp.VotingType
import java.util.*

@Entity
data class Voting (
    @PrimaryKey(autoGenerate = true)
    val votingId: Long = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "type")
    var type: VotingType,
    @ColumnInfo(name = "endTime")
    val endTime: Date,
    @ColumnInfo(name = "votingContent")
    val votingContent: String,
    @ColumnInfo(name = "quorum")
    val quorum: Int = -1,
    @ColumnInfo(name = "isOpen")
    val isOpen: Boolean,
    @ColumnInfo(name ="winnersNb")
    val winnersNb: Int = 1,
    @ColumnInfo(name ="votingCode")
    val votingCode: Long = -1

) {
    override fun toString(): String {
        return "Voting(votingId=$votingId, name='$name', type=$type, endTime=$endTime, votingContent='$votingContent', quorum=$quorum, isOpen=$isOpen, winnersNb=$winnersNb, votingCode=$votingCode)"
    }
}