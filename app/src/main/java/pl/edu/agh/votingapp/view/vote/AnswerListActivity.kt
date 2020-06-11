package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.comunication.client.VotingController
import pl.edu.agh.votingapp.comunication.model.Voting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswerListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: List<AnswerListElement>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)

        val votingConnector = VotingController().createConnector()

        votingConnector.loadVoting().enqueue(object : Callback<Voting> {
            override fun onResponse(call: Call<Voting>, response: Response<Voting>) {
                Log.d("BallotBull", response.body().toString())
                setData(response.body()!!)
            }

            override fun onFailure(call: Call<Voting>, t: Throwable) {
                Log.e("BallotBull", t.message!!)
            }
        })
    }

    private fun setData(voting: Voting) {
        val questionId = voting.answers.keys.toList()[0]
        myDataset = voting.answers[questionId]?.map {
            AnswerListElement(
                it.answerContent,
                0,
                it.questionId,
                it.answerId
            )
        }!!
        recyclerView = findViewById<RecyclerView>(R.id.candidate_list_rec_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AnswerListActivity)
            adapter = CandidateListAdapter(myDataset)
        }
    }


    private fun showProgram() {
        val intent = Intent(this, CandidateProgramActivity::class.java)
        startActivity(intent)
    }
}
