package pl.edu.agh.votingapp.view.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R

class VotesListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var votingList: Array<VoteListElement> = arrayOf<VoteListElement>(
        VoteListElement("Wybory samorządu Eestec", "Wybory samorządu na rok 2020"),
        VoteListElement("Starosta grupy", "Wybory na starostę grupy 2B"),
        VoteListElement("Co dziś na obiad", "Trudna decyzja")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votes_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = VoteListAdapter(votingList)

        recyclerView = findViewById<RecyclerView>(R.id.vote_list_rec_view).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
