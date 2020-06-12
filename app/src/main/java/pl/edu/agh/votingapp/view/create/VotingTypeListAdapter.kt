package pl.edu.agh.votingapp.view.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.VotingType

class VotingTypeListAdapter(
    private val fragment: StepChooseTypeFragment,
    private val votingTypeList: List<VotingType>
) : RecyclerView.Adapter<VotingTypeListAdapter.VotingTypeViewHolder>() {

    private var lastSelectedPos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VotingTypeViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.choose_type_list_item, parent, false) as View

        if (fragment.model.isVotingTypeInitialized()) {
            val selectedVotingType: VotingType = fragment.model.votingType
            lastSelectedPos = votingTypeList.first { it == selectedVotingType }.ordinal
        }

        return VotingTypeViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: VotingTypeViewHolder, position: Int) {
        val votingType: VotingType = votingTypeList[position]

        holder.bind(votingType)

        holder.itemView.isSelected = position == lastSelectedPos
        holder.itemView.setOnClickListener {
            fragment.model.votingType = votingType

            val copySelectedPos = lastSelectedPos
            lastSelectedPos = position
            notifyItemChanged(copySelectedPos)
            notifyItemChanged(lastSelectedPos)
        }
    }

    override fun getItemCount() = votingTypeList.size

    class VotingTypeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descTextView: TextView = itemView.findViewById(R.id.descTextView)

        fun bind(votingType: VotingType) {
            titleTextView.text = votingType.type
            descTextView.text = votingType.description
        }
    }

}

