package pl.edu.agh.votingapp.view.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_votings.*
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Voting
import pl.edu.agh.votingapp.viewmodel.list.ListVotingsViewModel

class ListVotingsActivity : AppCompatActivity() {

    val model: ListVotingsViewModel by viewModels()
    lateinit var listVotingsListAdapter: ListVotingsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_votings)

        model.votingList.observe(this,
            Observer { votings: List<Voting> ->
                listVotingsListAdapter.setVotingList(votings)
            })

        listVotingsListAdapter = ListVotingsListAdapter()
        val activity: ListVotingsActivity = this
        listVotings.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listVotingsListAdapter
        }
    }

}