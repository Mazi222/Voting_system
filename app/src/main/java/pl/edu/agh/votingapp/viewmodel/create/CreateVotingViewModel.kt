package pl.edu.agh.votingapp.viewmodel.create

import androidx.lifecycle.ViewModel
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.database.entities.Answers
import java.util.*

class CreateVotingViewModel : ViewModel() {
    lateinit var votingType: VotingType
    lateinit var content: String
    var endTime: Date? = null
    var quorum: Int = -1
    var numOfPeopleToChoose: Int = -1
    var numOfPeopleEntitled: Int? = Int.MAX_VALUE
    var isOpen: Boolean = true
    var answers: MutableList<Answers> = arrayListOf()

    fun isVotingTypeInitialized() = this::votingType.isInitialized
    fun isContentInitialized() = this::content.isInitialized

}