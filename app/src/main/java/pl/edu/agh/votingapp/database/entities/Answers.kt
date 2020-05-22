package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answers (
    @PrimaryKey(autoGenerate = true)
    val answerId: Long = 0,
    @ColumnInfo(name ="votingId")
    val votingId: Long,
//    val answerOwnerIds: List<Long>,
    @ColumnInfo(name ="answerContent")
    val answerContent: String,
    @ColumnInfo(name ="count")
    var count: Long
)