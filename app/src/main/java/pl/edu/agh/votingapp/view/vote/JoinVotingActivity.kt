package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import pl.edu.agh.votingapp.R
import android.util.Log
import androidx.appcompat.app.AlertDialog

class JoinVotingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_voting)
        val name = intent.getStringExtra("NAME")

        val etFirstName: EditText = findViewById(R.id.etFirstName) as EditText
        val etLastName: EditText = findViewById(R.id.etLastName) as EditText
        val etUserCode: EditText = findViewById(R.id.etUserCode) as EditText
        val tvVoteName: TextView = findViewById(R.id.tvVoteName) as TextView

        tvVoteName.text = name
        val firstName = etFirstName.text
        val lastName = etLastName.text
        val userCode = etUserCode.text

        val joinVotingButton: Button = findViewById(R.id.btn_login)
        joinVotingButton.setOnClickListener {
            if (firstName.trim().isEmpty()
                || lastName.trim().isEmpty()
                || userCode.trim().isEmpty()
            ) {
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val mAlertDialog = AlertDialog.Builder(this@JoinVotingActivity)
                mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
                mAlertDialog.setTitle("")
                mAlertDialog.setMessage("Are you sure you want to join this voting?")
                mAlertDialog.setPositiveButton("Yes") { _, _ ->

                    Log.d("BallotBull: join voting", "Join voting with name ${name!!}")
                    val intent = Intent(this, AnswerListActivity::class.java)
                    intent.putExtra("userName", "$firstName $lastName")
                    intent.putExtra("userCode", "$userCode")
                    startActivity(intent)
                }
                mAlertDialog.setNegativeButton("No") { _, _ ->
                }
                mAlertDialog.show()
            }
        }
    }
}
