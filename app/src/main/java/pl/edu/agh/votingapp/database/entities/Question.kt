package pl.edu.agh.votingapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    val questionId: Long = 0,
    @ColumnInfo(name = "votingId")
    var votingId: Long = 0,
    @ColumnInfo(name = "questionContent")
    var questionContent: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (questionId != other.questionId) return false
        if (votingId != other.votingId) return false
        if (questionContent != other.questionContent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = questionId.hashCode()
        result = 31 * result + votingId.hashCode()
        result = 31 * result + questionContent.hashCode()
        return result
    }
}