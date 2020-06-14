package pl.edu.agh.votingapp.comunication.model

import pl.edu.agh.votingapp.VotingType

data class VotingDto(
    val votingId: Long,
    val type: VotingType,
    val answers: List<AnswerDto>,
    val votingContent: String,
    val winnersNumber : Int
)