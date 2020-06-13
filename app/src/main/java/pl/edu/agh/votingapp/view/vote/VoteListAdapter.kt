package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.viewmodel.join.OngoingVoting
import pl.edu.agh.votingapp.viewmodel.join.ServerData
import java.io.Serializable

class VoteListAdapter(private val votingList: MutableList<OngoingVoting>) :
    RecyclerView.Adapter<VoteListAdapter.MyViewHolder>() {

    class MyViewHolder(
        val view: View,
        val parent: ViewGroup,
        val votingList: MutableList<OngoingVoting>
    ) : RecyclerView.ViewHolder(view) {
        val voteTitle: TextView = view.findViewById(R.id.vote_title) as TextView
        val voteDescription: TextView = view.findViewById(R.id.vote_description) as TextView
        val relativeLayout: RelativeLayout = view.findViewById(R.id.votesListRelativeLayout)

        fun joinVoting(position: Int) {
            val intent = Intent(parent.context, JoinVotingActivity::class.java)
            val ongoingVoting = votingList[position]
            intent.putExtra("NAME", ongoingVoting.name)
            ServerData.host = ongoingVoting.host
            ServerData.port = ongoingVoting.port
            parent.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.votes_list_item, parent, false)

        return MyViewHolder(cell, parent, votingList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.voteTitle.text = votingList[position].name
        holder.voteDescription.text = ""

        holder.relativeLayout.setOnClickListener {
            holder.joinVoting(position)
        }
    }

    override fun getItemCount() = votingList.size
}