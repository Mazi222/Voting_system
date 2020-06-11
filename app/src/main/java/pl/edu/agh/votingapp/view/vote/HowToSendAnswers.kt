package pl.edu.agh.votingapp.view.vote

import android.util.Log
import pl.edu.agh.votingapp.comunication.client.VotingController
import pl.edu.agh.votingapp.comunication.model.UserDto
import pl.edu.agh.votingapp.comunication.model.VoteResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// w activity w onCreate()

class HowToSend {

    fun sendingExample(answersList: List<Long>, userDto: UserDto) {

        val answers: MutableMap<Long, Long> = mutableMapOf()
        answersList.forEach { answerId ->
            answers.put(
                answerId,
                if (answers[answerId] == null) 1 else answers[answerId]!! + 1
            )
        }
        val request = VoteResponseDto(userDto, answers)
        val votingConnector = VotingController().createConnector()

        votingConnector.sendAnswers(request).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("BallotBull: votes send", response.body()!!)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("BallotBull: votes send ", t.message!!)

                }

            })
    }
}