package pl.edu.agh.votingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CandidateListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var myDataset: Array<CandidateListElement> = arrayOf(
        CandidateListElement("Adrian Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Rudy Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Rudy Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Adrian Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Rudy Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Adrian Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Rudy Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Adrian Gonzalez", android.R.drawable.ic_dialog_email),
        CandidateListElement("Rudy Gonzalez", android.R.drawable.ic_dialog_email)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CandidateListAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.candidate_list_rec_view).apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
}
