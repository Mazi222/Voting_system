package pl.edu.agh.votingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.votingapp.view.create.CreateVotingActivity
import pl.edu.agh.votingapp.view.vote.CandidateListActivity
import pl.edu.agh.votingapp.view.vote.VotesListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createVotingBtn: Button = findViewById(R.id.create_voting_btn)
        createVotingBtn.setOnClickListener {
            this.createVoting()
        }

        val joinVotingBtn: Button = findViewById(R.id.join_voting_btn)
        joinVotingBtn.setOnClickListener {
            this.joinVoting()
        }
    }

    private fun createVoting() {
        val intent = Intent(this, CreateVotingActivity::class.java)
        startActivity(intent)
    }
    private fun joinVoting() {
        val intent = Intent(this, VotesListActivity::class.java)
        startActivity(intent)
    }

}
