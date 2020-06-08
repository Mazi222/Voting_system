package pl.edu.agh.votingapp.comunication.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.view.vote.CandidateListElement
import pl.edu.agh.votingapp.viewmodel.join.ServerData

class VotingConnector {

    private val host = ServerData.host
    private val port = ServerData.port

    suspend fun getVotingData() : Array<CandidateListElement>{

        val client = HttpClient(Android){
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }

        // Start two requests asynchronously.
        val firstRequest = GlobalScope.async { client.get<VotingDescription>("https://$host:$port/voting") }

        // Get the request contents without blocking threads, but suspending the function until both
        // requests are done.
        val response = firstRequest.await()

        TODO("PARSE DATA")
        return arrayOf()
    }

}

class VotingDescription(votingId : Long, type : VotingType, questions : Array<Long>, answers : Array<Long>)

