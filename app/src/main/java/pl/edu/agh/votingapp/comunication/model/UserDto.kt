package pl.edu.agh.votingapp.comunication.model

data class UserDto(
    val votingId: Long,
    val userName: String,
    val userCode: Long
)