package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.comunication.client.VotingController
import pl.edu.agh.votingapp.comunication.model.UserDto
import pl.edu.agh.votingapp.comunication.model.VoteResponseDto
import pl.edu.agh.votingapp.comunication.model.VotingDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswerListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: List<AnswerListElement>
    private lateinit var confirmBtn: Button
    private lateinit var bordaAnswerMap: MutableMap<Long, Long>
    private lateinit var userDto: UserDto
    private lateinit var userName: String
    private lateinit var userCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)

        userName = intent.getStringExtra("userName")!!
        userCode = intent.getStringExtra("userCode")!!

        confirmBtn = findViewById(R.id.confirm_btn)

        bordaAnswerMap = mutableMapOf()

        val votingConnector = VotingController().createConnector()

        votingConnector.loadVoting().enqueue(object : Callback<VotingDto> {
            override fun onResponse(call: Call<VotingDto>, response: Response<VotingDto>) {
                Log.d("BallotBull:", "Get voting response: " + response.body().toString())
                setData(response.body()!!)
            }

            override fun onFailure(call: Call<VotingDto>, t: Throwable) {
                Log.e("BallotBull", t.message!!)
            }
        })
    }

    private fun setData(votingDto: VotingDto) {
        myDataset = votingDto.answers.map {
            AnswerListElement(
                it.answerContent,
                0,
                it.questionId,
                it.answerId
            )
        }
        userDto = UserDto(votingDto.votingId, userName, userCode.toLong())
        myDataset.forEach {
            bordaAnswerMap.put(it.answerId, 0)
        }

        recyclerView = findViewById<RecyclerView>(R.id.candidate_list_rec_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AnswerListActivity)

            title = votingDto.votingContent

            when(votingDto.type) {
                VotingType.BORDA_COUNT -> {
                    adapter = BordaCountAdapter(myDataset,this@AnswerListActivity)
                    confirmBtn.setOnClickListener {
                        if(isValidAnswer(myDataset.size)) {
                            val request = VoteResponseDto(userDto, bordaAnswerMap)
                            val votingConnector = VotingController().createConnector()

                            votingConnector.sendAnswers(request).enqueue(
                                object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
//                                        Log.d("BallotBull: votes send", response.body()!!)
                                        Log.d("BallotBull: votes send", response.toString())
                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Log.d("BallotBull: votes send ", t.message!!)

                                    }
                                })
                            Log.d("Final Map: ", bordaAnswerMap.toString())
                        } else {
                            Toast.makeText(this@AnswerListActivity, "Incorrect input", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else -> {
                    adapter = CandidateListAdapter(myDataset, userDto)
                    confirmBtn.visibility = View.INVISIBLE
                }
            }
        }
    }


    fun updateAnswerMap(answerId: Long, points: Long) {
        bordaAnswerMap.put(answerId, points)
    }

    fun isValidAnswer(size: Int): Boolean {
        var pointSum: Long = 0
        bordaAnswerMap.forEach {
            pointSum += it.value
        }
        var expectedPointSum: Long = 0
        for (i in 1..size) {
            expectedPointSum += i
        }
        return expectedPointSum == pointSum
    }

    private fun showProgram() {
        val intent = Intent(this, CandidateProgramActivity::class.java)
        startActivity(intent)
    }
}
