package pl.edu.agh.votingapp.comunication.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.edu.agh.votingapp.comunication.model.VoteResponse
import pl.edu.agh.votingapp.comunication.model.Voting
import pl.edu.agh.votingapp.viewmodel.join.ServerData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST

interface VoteConnector {

    @GET("/voting")
    fun loadVoting(): Call<Voting>

    @POST("/voting")
    fun sendAnswers(answers: VoteResponse): Call<String>
}

class VotingController {

    fun createConnector(): VoteConnector {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(VoteConnector::class.java)
    }

    companion object {
        val BASE_URL = ServerData.getUrl()
    }
}