package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import pl.edu.agh.votingapp.MainActivity
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.comunication.client.VotingController
import pl.edu.agh.votingapp.comunication.model.UserDto
import pl.edu.agh.votingapp.comunication.model.VoteResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateVoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        val name: String = intent.getStringExtra("name") !!
        val votingId: Long = intent.getLongExtra("votingId", -1)
        val userName: String = intent.getStringExtra("userName")!!
        val userCode: Long = intent.getLongExtra("userCode", -1)
        val answerId: Long = intent.getLongExtra("answerId", -1)

        var answerMap: MutableMap<Long, Long> = mutableMapOf()
        answerMap[answerId] = 1

        val userDto = UserDto(votingId, userName, userCode)

        val candidateName: TextView = findViewById(R.id.candidate_name)
        candidateName.text = name

        val confirmVoteButton: Button = findViewById(R.id.btn_confirm_vote)
        confirmVoteButton.setOnClickListener {
            confirmVote(answerMap, userDto)
        }
    }

    fun confirmVote(answerMap: MutableMap<Long, Long>, userDto: UserDto) {
        val request = VoteResponseDto(userDto, answerMap)
        val votingConnector = VotingController().createConnector()

        votingConnector.sendAnswers(request).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    Log.d("BallotBull: votes send", response.body()!!)
                    Log.d("BallotBull: votes send", response.message())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("BallotBull: votes send ", t.message!!)

                }
            })

        Toast.makeText(this@CandidateVoteActivity, "Thank you for voting", Toast.LENGTH_SHORT).show()
        val i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)

    }
}
