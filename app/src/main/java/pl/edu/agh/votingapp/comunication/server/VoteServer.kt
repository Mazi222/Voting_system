package pl.edu.agh.votingapp.comunication.server

import android.util.Log
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import pl.edu.agh.votingapp.comunication.model.Answer
import pl.edu.agh.votingapp.comunication.model.Question
import pl.edu.agh.votingapp.comunication.model.VoteResponse
import pl.edu.agh.votingapp.comunication.model.Voting
import java.util.concurrent.TimeUnit

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answers

class VoteServer {

    private val db: AppDatabase = AppDatabase.getInstance()
    private val questionDAO: QuestionDAO = db.QuestionDAO()
    private val votingDao: VotingDAO = db.VotingDAO()
    private val answersDAO: AnswersDAO = db.AnswersDAO()
    private lateinit var server: ApplicationEngine

    fun startServer(createdPort: Int) {
        val voting = votingDao.getWithMaxId()
        val questions = questionDAO.loadAllQuestions(voting.votingId)
        val answers = answersDAO.loadAllAnswers(voting.votingId)
        val answersMap = answers.associateBy { it.answerId }

        server = embeddedServer(Netty, createdPort) {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()

                }
            }
            routing {
                get("/voting") {
                    val message = Voting(
                        voting.votingId,
                        voting.type,
                        questions.map {
                            Question(
                                it.questionId,
                                it.votingId,
                                it.questionContent
                            )
                        },
                        answers.map {
                            Answer(
                                it.answerId,
                                it.votingId,
                                it.questionId,
                                it.answerContent
                            )
                        }.groupBy { it.questionId })

                    Log.d("BallotBull", message.toString())
                    call.respond(message)
                }

                post("/voting") {
                    parseVote(voting.isOpen, answersMap)
                    call.respond(HttpStatusCode.OK, "Votes case")
                }
            }
        }.start(true)
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.parseVote(
        isOpen: Boolean,
        answersMap: Map<Long, Answers>
    ) {
        val request = call.receive<VoteResponse>()
        Log.d("BallotBull", request.toString())

        request.answersIds.forEach { answerId ->
            val answer = answersMap[answerId]
            if (answer != null) {
                answer.count += 1
                if (isOpen) {
                    answer.voters?.add(request.userCode)
                }
            }

        }
    }

    fun stopServer() {
        server.stop(1, 1, TimeUnit.SECONDS)
    }

}