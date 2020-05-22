package pl.edu.agh.votingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CreateVotingActivity : AppCompatActivity() {

    var newVoting: Voting = Voting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_voting)

        val chooseTypeFragment = CreateVotingChooseTypeFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_create_voting_fragment, chooseTypeFragment)
            commit()
        }
    }
}
