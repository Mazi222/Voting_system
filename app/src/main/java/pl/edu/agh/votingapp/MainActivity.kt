package pl.edu.agh.votingapp

import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.votingapp.receiver.WifiReceiver
import pl.edu.agh.votingapp.view.create.CreateVotingActivity
import pl.edu.agh.votingapp.view.list.ListVotingsActivity
import pl.edu.agh.votingapp.view.vote.VotesListActivity

class MainActivity : AppCompatActivity() {

    private val wifiReceiver = WifiReceiver()

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

        val listMyVotingsBtn: Button = findViewById(R.id.list_votings_btn)
        listMyVotingsBtn.setOnClickListener {
            this.listMyVotings()
        }

        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        applicationContext.registerReceiver(wifiReceiver, intentFilter)
    }

    private fun createVoting() {
        val intent = Intent(this, CreateVotingActivity::class.java)
        startActivity(intent)
    }

    private fun joinVoting() {
        val intent = Intent(this, VotesListActivity::class.java)
        startActivity(intent)
    }

    private fun listMyVotings() {
        val intent = Intent(this, ListVotingsActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        applicationContext.unregisterReceiver(wifiReceiver)
    }
}
