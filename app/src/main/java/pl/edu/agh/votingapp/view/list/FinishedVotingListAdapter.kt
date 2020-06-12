package pl.edu.agh.votingapp.view.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Voting

class FinishedVotingListAdapter(
    private val finishedVotingListActivity: FinishedVotingListActivity
) : RecyclerView.Adapter<FinishedVotingListAdapter.FinishedVotingViewHolder>() {

    private var votingList: List<Voting>? = null

    fun setVotingList(votingList: List<Voting>) {
        this.votingList = votingList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedVotingViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.finished_voting_list_item, parent, false) as View

        return FinishedVotingViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: FinishedVotingViewHolder, position: Int) {
        votingList?.let {
            val voting = it[position]
            holder.bind(voting)

            holder.linearLayout.setOnClickListener {
                val intent = Intent(finishedVotingListActivity, FinishedVotingActivity::class.java)
                intent.putExtra("VOTING_ID", voting.votingId)
                finishedVotingListActivity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (votingList == null) {
            0
        } else {
            votingList!!.size
        }
    }

    class FinishedVotingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.votingTitle)
        private val typeTextView: TextView = itemView.findViewById(R.id.votingType)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.finishedVotingListLinearLayout)

        fun bind(voting: Voting) {
            titleTextView.text = voting.name
            typeTextView.text = voting.type.type
        }
    }
}