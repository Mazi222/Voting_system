package pl.edu.agh.votingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answers (
    @PrimaryKey(autoGenerate = true)
    val answerId: Long = 0,
    val votingId: Long,
    val answerOwnerIds: List<Long>,
    val answerContent: String,
    var count: Long
)