package pl.edu.agh.votingapp.comunication.model

data class VoteResponseDto(val userDto: UserDto, val answersIdToCount: Map<Long, Long>)
