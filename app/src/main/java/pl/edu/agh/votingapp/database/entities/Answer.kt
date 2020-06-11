package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answer (
    @PrimaryKey(autoGenerate = true)
    val answerId: Long = 0,
    @ColumnInfo(name = "votingId")
    var votingId: Long = 0,
    @ColumnInfo(name = "questionId")
    var questionId: Long = 0,
    @ColumnInfo(name = "voters")
    val voters: MutableList<Long>? = arrayListOf(),
    @ColumnInfo(name ="answerContent")
    var answerContent: String = "",
    @ColumnInfo(name ="count")
    var count: Long = 0
)