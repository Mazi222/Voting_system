package pl.edu.agh.votingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answers (
    @PrimaryKey(autoGenerate = true)
    val answerId: Int,
    val votingId: Int,
    val answerOwnerIds: List<Int>,
    val answerContent: String,
    var count: Long
)