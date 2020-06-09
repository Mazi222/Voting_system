package pl.edu.agh.votingapp.view.list

//import pl.edu.agh.votingapp.comunication.server.ServerRegistration
//import pl.edu.agh.votingapp.comunication.server.VoteServer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.votingapp.R
import java.net.ServerSocket

class OngoingVotingActivity : AppCompatActivity() {

//    private val server = VoteServer()
//    private val nsdRegistrator = ServerRegistration()

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
//        server.startServer(socket.localPort)

        // register service
//        nsdRegistrator.registerServer(votingName, socket.localPort)


        val timeToEnd = intent.getLongExtra("VOTING_END_MILLIS", 60 * 60 * 10)
//        Handler().postDelayed({
//            server.stopServer()
//        }, timeToEnd)
    }

//    fun finishVotingEarly() {
//        val finishVotingButton: Button = findViewById(R.id.finish_voting_button)
//        finishVotingButton.setOnClickListener {
//            server.stopServer()
//        }
//    }

}
