package pl.edu.agh.votingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey(autoGenerate = true)
    val questionId: Int,
    val votingId: Int,
    val questionContent: String
)