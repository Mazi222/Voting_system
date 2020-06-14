package pl.edu.agh.votingapp.comunication.server

import android.util.Log
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.util.pipeline.PipelineContext
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.comunication.model.AnswerDto
import pl.edu.agh.votingapp.comunication.model.VoteResponseDto
import pl.edu.agh.votingapp.comunication.model.VotingDto
import java.util.concurrent.TimeUnit

import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.dao.AnswersDAO
import pl.edu.agh.votingapp.database.dao.QuestionDAO
import pl.edu.agh.votingapp.database.dao.VotingDAO
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.database.entities.User
import pl.edu.agh.votingapp.database.entities.Voting
import pl.edu.agh.votingapp.votings.*
import java.net.InetAddress

class VoteServer {

    private val db: AppDatabase = AppDatabase.getInstance()
    private val questionDAO: QuestionDAO = db.QuestionDAO()
    private val votingDao: VotingDAO = db.VotingDAO()
    private val answersDAO: AnswersDAO = db.AnswersDAO()

    companion object {
        var server: ApplicationEngine? = null

        fun stopServer() {
            if (isWorking()) {
                Log.d("BallotBull", "Server stopped")
                server?.stop(1, 1, TimeUnit.SECONDS)
                server = null
            } else {
                Log.e("BallotBull", "Can't stop server")
            }
        }
        fun isWorking() : Boolean {
            return server != null
        }
    }

    fun startServer(createdPort: Int, host: InetAddress) {
        val voting = votingDao.getWithMaxId()
        val answers = answersDAO.loadAllAnswers(voting.votingId)
        val ongoingVoting: BaseVoting = when (voting.type) {
            VotingType.BORDA_COUNT -> BordaCount(db)
            VotingType.FIRST_PAST_THE_POST -> FirstPastThePostVoting(db)
            VotingType.SINGLE_NON_TRANSFERABLE_VOTE -> SingleNonTransferableVote(db)
            VotingType.TWO_ROUND_SYSTEM, VotingType.MAJORITY_VOTE -> MajorityVote(db)
            VotingType.NONE -> throw RuntimeException("Voting must have one of folowing types. Impossible state.")
        }

        Log.d("BallotBull", "Server created with voting ${voting.toString()}")
        server = embeddedServer(Netty, createdPort, host = host.hostAddress) {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }
            }
            routing {
                get("/voting") {
                    sendVotingData(voting, answers)
                }

                post("/voting") {
                    receiveVote(ongoingVoting, voting)
                }
            }
        }
        (server as NettyApplicationEngine).start(true)
        Log.e("BallotBull", "Server started $server")
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.sendVotingData(
        voting: Voting,
        answers: List<Answer>
    ) {
        val message = VotingDto(
            voting.votingId,
            voting.type,
            answers.map {
                AnswerDto(
                    it.answerId,
                    it.votingId,
                    it.questionId,
                    it.answerContent
                )
            },
            voting.votingContent,
            voting.winnersNb
        )

        Log.d("BallotBull", "Client connected to voting")
        call.respond(message)
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.receiveVote(
        ongoingVoting: BaseVoting,
        voting: Voting
    ) {
        val request = call.receive<VoteResponseDto>()
        Log.d("BallotBull", "Incoming vote $request")
        val userDto = request.userDto
        val answersMap = request.answersIdToCount
        ongoingVoting.addUser(
            User(
                votingId = voting.votingId,
                userName = userDto.userName,
                userCode = userDto.userCode
            )
        )
        answersMap.forEach { (answerId, counter) ->
            ongoingVoting.updateAnswerCount(
                voting.votingId, userDto.userName, answerId, counter
            )
        }
        call.respond(HttpStatusCode.OK, "Votes case")
    }
}