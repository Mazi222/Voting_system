package pl.edu.agh.votingapp.view.list

import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Voting
import pl.edu.agh.votingapp.viewmodel.list.FinishedVotingViewModel

class FinishedVotingActivity : AppCompatActivity() {

    val model: FinishedVotingViewModel by viewModels()
    private lateinit var voting: Voting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_voting)

        val finishDateView: TextView = findViewById(R.id.finish_date)
        val votingNameView: TextView = findViewById(R.id.voting_name)
        val votingDescriptionView: TextView = findViewById(R.id.voting_description)
        val votingTypeView: TextView = findViewById(R.id.voting_type)
        val showResultsBtn: Button = findViewById(R.id.show_results_btn)

        val votingId = intent.getLongExtra("VOTING_ID", 0L)
        lifecycleScope.launch {
            voting = model.getVotingById(votingId)

            title = voting.name

            // set view
            votingNameView.text = voting.name
            votingTypeView.text = voting.type.type
            votingDescriptionView.text = voting.votingContent
            finishDateView.text = DateFormat.format(
                "EEE, MMM dd yyyy, HH:mm",
                voting.endTime
            )
            showResultsBtn.setOnClickListener {
                Toast.makeText(
                    this@FinishedVotingActivity,
                    "TODO: Show results",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}
