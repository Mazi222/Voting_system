package pl.edu.agh.votingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.votingapp.view.create.CreateVotingActivity

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
    private fun joinVoting() {  // navigates to CandidateListActivity, need to be changed later
        val intent = Intent(this, CandidateListActivity::class.java)
        startActivity(intent)
    }

}
