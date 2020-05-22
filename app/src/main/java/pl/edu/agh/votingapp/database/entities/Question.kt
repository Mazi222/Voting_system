package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey(autoGenerate = true)
    val questionId: Long = 0,
    @ColumnInfo(name ="votingId")
    val votingId: Long,
    @ColumnInfo(name ="questionContent")
    val questionContent: String
)