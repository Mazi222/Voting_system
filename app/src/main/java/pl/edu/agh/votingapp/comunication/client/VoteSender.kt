package pl.edu.agh.votingapp.comunication.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pl.edu.agh.votingapp.viewmodel.join.ServerData

class VoteSender {

    private val host = ServerData.host
    private val port = ServerData.port

    suspend fun sendVotes(votes: List<Answer>) {
        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }

        GlobalScope.async {
            client.post<Array<Answer>>("https://$host:$port/voting") {
                body = votes
            }
        }

    }

}

class Answer(val answerId: Long, questionId: Long, userCode: Long?)