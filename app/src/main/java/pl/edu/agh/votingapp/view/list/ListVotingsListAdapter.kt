package pl.edu.agh.votingapp.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Voting

class ListVotingsListAdapter :
    RecyclerView.Adapter<ListVotingsListAdapter.ListVotingsViewHolder>() {

    private var votingList: List<Voting>? = null

    fun setVotingList(votingList: List<Voting>) {
        this.votingList = votingList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVotingsViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.server_voting_list_item, parent, false) as View

        return ListVotingsViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ListVotingsViewHolder, position: Int) {
        votingList?.let {
            val voting = it[position]
            holder.bind(voting)
        }
    }

    override fun getItemCount(): Int {
        return if (votingList == null) {
            0
        } else {
            votingList!!.size
        }
    }

    class ListVotingsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.votingTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.votingDescription)

        fun bind(voting: Voting) {
            titleTextView.text = voting.votingContent
            descriptionTextView.text = voting.type.type
        }
    }
}