package pl.edu.agh.votingapp.view.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_finished_voting_list.*
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Voting
import pl.edu.agh.votingapp.viewmodel.list.FinishedVotingViewModel

class FinishedVotingListActivity : AppCompatActivity() {

    val model: FinishedVotingViewModel by viewModels()
    lateinit var finishedVotingListAdapter: FinishedVotingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_voting_list)
        title = "Finished votings"

        model.votingList.observe(this,
            Observer { votings: List<Voting> ->
                if (votings.isEmpty()) {
                    listVotings.visibility = View.GONE
                    noDataFoundText.visibility = View.VISIBLE
                } else {
                    listVotings.visibility = View.VISIBLE
                    noDataFoundText.visibility = View.GONE
                    finishedVotingListAdapter.setVotingList(votings)
                }
            })

        finishedVotingListAdapter = FinishedVotingListAdapter(this)
        listVotings.apply {
            layoutManager = LinearLayoutManager(this@FinishedVotingListActivity)
            adapter = finishedVotingListAdapter
        }
    }

}