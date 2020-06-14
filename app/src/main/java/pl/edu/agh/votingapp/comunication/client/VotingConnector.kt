package pl.edu.agh.votingapp.comunication.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.edu.agh.votingapp.comunication.model.VoteResponseDto
import pl.edu.agh.votingapp.comunication.model.VotingDto
import pl.edu.agh.votingapp.viewmodel.join.ServerData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface VoteConnector {

    @GET("/voting")
    fun loadVoting(): Call<VotingDto>

    @POST("/voting")
    fun sendAnswers(@Body answers: VoteResponseDto): Call<String>
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
        val BASE_URL =  ServerData.getUrl()
    }
}