package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R

class VoteListAdapter(private val votingList: Array<VoteListElement>) :
    RecyclerView.Adapter<VoteListAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View, val parent: ViewGroup) : RecyclerView.ViewHolder(view) {
        val voteTitle: TextView = view.findViewById(R.id.vote_title) as TextView
        val voteDescription: TextView = view.findViewById(R.id.vote_description) as TextView
        val relativeLayout: RelativeLayout = view.findViewById(R.id.votesListRelativeLayout)

        fun joinVoting() {
            val intent = Intent(parent.context, JoinVotingActivity::class.java)
            parent.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.votes_list_item, parent, false)

        return MyViewHolder(cell, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listElement: VoteListElement = votingList[position]
        holder.voteTitle.text = votingList[position].title
        holder.voteDescription.text = votingList[position].description

        holder.relativeLayout.setOnClickListener {
            holder.joinVoting()
        }

    }

    override fun getItemCount() = votingList.size
}