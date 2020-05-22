package pl.edu.agh.votingapp.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val userId: Int,
    val votingId: Int,
    val userName: String?,
    val userCode: Int
)