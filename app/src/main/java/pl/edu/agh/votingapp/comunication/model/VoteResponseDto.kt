package pl.edu.agh.votingapp.comunication.model

class VoteResponseDto(val userDto: UserDto, val answersIdToCount: MutableMap<Long, Long>)
