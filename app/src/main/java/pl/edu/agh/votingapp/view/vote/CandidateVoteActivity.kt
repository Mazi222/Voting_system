package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pl.edu.agh.votingapp.R

class CandidateVoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        val name: String = intent.getStringExtra("name")

        val candidateName: TextView = findViewById(R.id.candidate_name)
        candidateName.text = name

        val confirmVoteButton: Button = findViewById(R.id.btn_confirm_vote)
        confirmVoteButton.setOnClickListener {
            confirmVote()
        }
    }

    fun confirmVote() {
        val intent = Intent(this, VoteGivenActivity::class.java)
        startActivity(intent)
    }
}
