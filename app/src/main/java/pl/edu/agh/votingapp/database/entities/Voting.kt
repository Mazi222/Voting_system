package pl.edu.agh.votingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import pl.edu.agh.votingapp.VotingType
import java.sql.Time

@Entity
data class Voting (
    @PrimaryKey(autoGenerate = true)
    val votingId: Int,
    val type: VotingType,
    val endTime: Time,
    val votingContent: String,
    val isOpen: Boolean,

    @Relation(
        parentColumn = "votingId",
        entityColumn = "votingId"
    )
    val questions: List<Question>,
    @Relation(
        parentColumn = "votingId",
        entityColumn = "votingId"
    )
    val users: List<User>,
    @Relation(
        parentColumn = "votingId",
        entityColumn = "votingId"
    )
    val answers: List<Answers>
)