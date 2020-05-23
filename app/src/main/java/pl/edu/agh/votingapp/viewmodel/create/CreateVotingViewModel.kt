package pl.edu.agh.votingapp.viewmodel.create

import androidx.lifecycle.ViewModel
import pl.edu.agh.votingapp.VotingType
import java.util.*

class CreateVotingViewModel : ViewModel() {
    lateinit var votingType: VotingType
    lateinit var content: String
    lateinit var endTime: Date
    var quorum: Int = 0
    var numOfPeopleEntitled: Int = 1
    var numOfPeopleToChoose: Int = 1
    var isOpen: Boolean = false

    fun isVotingTypeInitialized() = this::votingType.isInitialized
    fun isContentInitialized() = this::content.isInitialized
    fun isEndTimeInitialized() = this::content.isInitialized

}