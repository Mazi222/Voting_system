package pl.edu.agh.votingapp.comunication.model

data class QuestionDto(
    val questionId: Long,
    val votingId: Long,
    val questionContent: String
)