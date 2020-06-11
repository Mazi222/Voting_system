package pl.edu.agh.votingapp.comunication.model

class Answer(
    val answerId: Long,
    var votingId: Long,
    var questionId: Long,
    var answerContent: String
)