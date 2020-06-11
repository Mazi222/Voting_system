package pl.edu.agh.votingapp.view.vote

import android.util.Log
import pl.edu.agh.votingapp.comunication.client.VotingController
import pl.edu.agh.votingapp.comunication.model.VoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// w activity w onCreate()

class HowToSend {

    fun sendingExample(answersList: List<Long>, userCode: Long) {

        val request = VoteResponse(userCode, answersList)
        val votingConnector = VotingController().createConnector()

        votingConnector.sendAnswers(request).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("BallotBull", response.body()!!)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("BallotBull", t.message!!)

                }

            })
    }
}