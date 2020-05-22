package pl.edu.agh.votingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey(autoGenerate = true)
    val questionId: Long = 0,
    val votingId: Long,
    val questionContent: String
)