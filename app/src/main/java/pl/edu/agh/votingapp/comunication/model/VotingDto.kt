package pl.edu.agh.votingapp.comunication.model

import pl.edu.agh.votingapp.VotingType

class VotingDto(
    val votingId: Long,
    val type: VotingType,
    val questions: List<QuestionDto>,
    val answers: Map<Long, List<AnswerDto>>,
    val code: Long,
    val votingContent: String
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
        var answers_: Map<Long, List<AnswerDto>> = mapOf()
        var questions_: List<QuestionDto> = listOf()
        var code_ : Long = -1
    }
}