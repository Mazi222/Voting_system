package pl.edu.agh.votingapp.database.converters

import androidx.room.TypeConverter
import pl.edu.agh.votingapp.VotingType
import java.sql.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromVotingTypeToInt(value: VotingType?): Int?{
        return when(value){
            VotingType.MAJORITY_VOTE -> 1
            VotingType.BORDA_COUNT -> 2
            VotingType.FIRST_PAST_THE_POST -> 3
            VotingType.TWO_ROUND_SYSTEM -> 4
            VotingType.SINGLE_NON_TRANSFERABLE_VOTE -> 5
            else -> 0
        }
    }

    @TypeConverter
    fun fromVIntToVotingType(value: Int?): VotingType?{
        return when(value){
            1 -> VotingType.MAJORITY_VOTE
            2 -> VotingType.BORDA_COUNT
            3 -> VotingType.FIRST_PAST_THE_POST
            4 -> VotingType.TWO_ROUND_SYSTEM
            5 -> VotingType.SINGLE_NON_TRANSFERABLE_VOTE
            else -> VotingType.NONE
        }
    }
}