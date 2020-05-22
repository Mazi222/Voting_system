package pl.edu.agh.votingapp.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey
    val questionId: Int,
    val votingOwnerId: Int,
    val questionContent: String
)