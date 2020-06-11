package pl.edu.agh.votingapp.comunication.model

import pl.edu.agh.votingapp.VotingType

class Voting(
    val votingId: Long,
    val type: VotingType,
    val questions: List<Question>,
    val answers: Map<Long, List<Answer>>,
    val code : Long
) {

    init {
        votingId_ = votingId
        type_ = type
        answers_ = answers
        questions_ = questions
        code_ = code
    }

    companion object {
        var votingId_: Long = 0
        var type_: VotingType = VotingType.NONE
        var answers_: Map<Long, List<Answer>> = mapOf()
        var questions_: List<Question> = listOf()
        var code_ : Long = -1
    }
}