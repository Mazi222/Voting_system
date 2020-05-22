package pl.edu.agh.votingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val votingId: Int,
    val userName: String?,
    val userCode: Int
)