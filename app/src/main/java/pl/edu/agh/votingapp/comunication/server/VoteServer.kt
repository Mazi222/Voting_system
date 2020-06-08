package pl.edu.agh.votingapp.comunication.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import java.util.concurrent.TimeUnit

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers
import pl.edu.agh.votingapp.database.entities.Voting

class VoteServer {

    private val db: AppDatabase = AppDatabase.getInstance()
    private val questionDAO: QuestionDAO = db.QuestionDAO()
    private val votingDao: VotingDAO = db.VotingDAO()
    private val answersDAO: AnswersDAO = db.AnswersDAO()
    private lateinit var server : ApplicationEngine

    fun startServer( createdPort: Int) {
        val voting = votingDao.getWithMaxId()
        val questions = questionDAO.loadAllQuestions(voting.votingId)
        val answers = answersDAO.loadAllAnswers(voting.votingId)
        val answersMap = answers.associateBy { it.answerId }

        server = embeddedServer(Netty, createdPort) {
            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT) // Pretty Prints the JSON
                }
            }
            routing {
                get("/voting") {
                    call.respond(mapOf("votingId" to voting.votingId, "type" to voting.type, "questions" to questions, "answers" to answers))
                }

                post("/voting") {
                    parseVote(voting.isOpen, answersMap)
                }
            }
        }.start(true)
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.parseVote(
        isOpen : Boolean,
        answersMap: Map<Long, Answers>
    ) {
        var parameters = call.parameters
        val request = call.receiveRequest<VoteResponse>()

        request.answersIds.forEach { answerId ->
            val answer = answersMap[answerId]
            if (answer != null) {
                answer.count += 1
                if(isOpen) {
                    answer.voters?.add(request.userCode)
                }
            }

        }
    }

    fun stopServer() {
        server.stop(1, 1, TimeUnit.SECONDS)
    }

    private suspend inline fun <reified T : Any> ApplicationCall.receiveRequest(): T {
        val request = this.receiveText()
        return objectMapper.readValue(request)
    }

    companion object {
        private val objectMapper = ObjectMapper()
            .registerKotlinModule()
    }

    class VoteResponse(val userName : String, val userCode : Long, val  answersIds : Array<Long>)
}