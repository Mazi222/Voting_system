package pl.edu.agh.votingapp.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answers (
    @PrimaryKey
    val answerId: Int,
    val answerOwnerIds: List<Int>,
    val answerContent: String,
    var count: Long
)