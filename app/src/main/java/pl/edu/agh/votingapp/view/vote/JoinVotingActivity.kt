package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pl.edu.agh.votingapp.R

class JoinVotingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_voting)

        val joinVotingButton: Button = findViewById(R.id.btn_login)
        joinVotingButton.setOnClickListener {
            val intent = Intent(this, CandidateListActivity::class.java)
            startActivity(intent)
        }
    }
}
