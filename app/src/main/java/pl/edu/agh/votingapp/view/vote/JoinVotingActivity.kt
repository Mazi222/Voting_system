package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.viewmodel.join.ServerData
import java.net.InetAddress

class JoinVotingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_voting)
        var name = intent.getStringExtra("NAME")
        var host = intent.getSerializableExtra("HOST") as InetAddress
        var port = intent.getIntExtra("PORT", 8080)

        TODO("TUTAJ POTRZEBNA JEST ZMIANA WIDOKU")
//        - pole na userCode : Long? i przekazanie go przez intent dalej
//        - wyświetlenie nazwy głosowania
//        - dodanie popupu "czy na pewno chcesz dołaczyć"

        val joinVotingButton: Button = findViewById(R.id.btn_login)
        joinVotingButton.setOnClickListener {
            val intent = Intent(this, CandidateListActivity::class.java)
            ServerData.host = host
            ServerData.port = port
            startActivity(intent)
        }
    }
}
