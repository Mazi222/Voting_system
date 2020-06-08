package pl.edu.agh.votingapp.view.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.comunication.client.ServerDiscovery
import pl.edu.agh.votingapp.viewmodel.join.OngoingVotings

class VotesListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var votingList: OngoingVotings
    private lateinit var serverDiscovery : ServerDiscovery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votes_list)

        votingList = OngoingVotings()
        serverDiscovery = ServerDiscovery(this, viewAdapter, votingList)
        viewManager = LinearLayoutManager(this)
        viewAdapter = VoteListAdapter(votingList.getVotings())

        recyclerView = findViewById<RecyclerView>(R.id.vote_list_rec_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        serverDiscovery.discoverServices()
    }
}
