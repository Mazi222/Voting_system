package pl.edu.agh.votingapp.view.create

import android.content.Context
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.text.format.Formatter.formatIpAddress
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.comunication.server.ServerRegistration
import pl.edu.agh.votingapp.comunication.server.VoteServer
import java.net.InetAddress

class OngoingVotingActivity : AppCompatActivity() {

    private val server = VoteServer()
    private lateinit var nsdRegistrator : ServerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ongoing_voting)
        // set view
        val finishDateView = findViewById<TextView>(R.id.finish_date)
        finishDateView.text = intent.getSerializableExtra("VOTING_END_DATE")?.toString()

        val codeView = findViewById<TextView>(R.id.code_view)
        codeView.text = intent.getLongExtra("CODE", -1).toString()


        val votingName = intent.getStringExtra("VOTING_NAME")!!
        val votingNameView = findViewById<TextView>(R.id.voting_name)
        votingNameView.text = votingName

        // create server
        AsyncTask.execute { server.startServer(8080) }

        // register service
        val host = getIpAddress()
        Log.e("BallotBull", "Current server IP: $host")
        nsdRegistrator = ServerRegistration(this)
        nsdRegistrator.registerServer(votingName, 8080, host!!)

        val timeToEnd = intent.getLongExtra("VOTING_END_MILLIS", 60 * 60 * 10)
        Handler().postDelayed({
            server.stopServer()
//            server.unregisterService
        }, timeToEnd)

        val finishVotingButton: Button = findViewById(R.id.finish_voting_button)
        finishVotingButton.setOnClickListener {
            Log.d("BallotBull", "Finish voting early")
            server.stopServer()
        }
    }

    @Suppress("DEPRECATION")
    private fun getIpAddress(): InetAddress? {
        val wm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return InetAddress.getByName(formatIpAddress(wm.connectionInfo.ipAddress))
    }
}