package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    @ColumnInfo(name ="votingId")
    val votingId: Long,
    @ColumnInfo(name ="userName")
    val userName: String?,
    @ColumnInfo(name ="userCode")
    val userCode: Long
)