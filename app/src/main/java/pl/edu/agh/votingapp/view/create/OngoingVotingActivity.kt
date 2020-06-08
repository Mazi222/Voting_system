package pl.edu.agh.votingapp.view.create

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.widget.Button
import android.widget.TextView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.comunication.server.ServerRegistration
import pl.edu.agh.votingapp.comunication.server.VoteServer
import java.net.ServerSocket

class OngoingVotingActivity : AppCompatActivity() {

    private val server = VoteServer()
    private lateinit var nsdRegistrator : ServerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ongoing_voting)
        // set view
        val finishDateView = findViewById<TextView>(R.id.finish_date)
        finishDateView.text = intent.getStringExtra("VOTING_END_DATE")!!

        val votingName = intent.getStringExtra("VOTING_NAME")!!
        val votingNameView = findViewById<TextView>(R.id.voting_name)
        votingNameView.text = votingName

        // create port
        val socket = ServerSocket(0)

        // create server
        server.startServer(socket.localPort)

        // register service
        nsdRegistrator = ServerRegistration(this)
        nsdRegistrator.registerServer(votingName, socket.localPort)


        val timeToEnd = intent.getLongExtra("VOTING_END_MILLIS", 60 * 60 * 10)
        Handler().postDelayed({
            server.stopServer()
        }, timeToEnd)
    }

    fun finishVotingEarly() {
        val finishVotingButton: Button = findViewById(R.id.finish_voting_button)
        finishVotingButton.setOnClickListener {
            server.stopServer()
        }
    }

}