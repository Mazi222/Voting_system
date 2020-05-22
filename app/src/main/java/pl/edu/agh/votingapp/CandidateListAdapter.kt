package pl.edu.agh.votingapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CandidateListAdapter(private val myDataset: Array<CandidateListElement>) :
    RecyclerView.Adapter<CandidateListAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.candidate_name) as TextView
        val candidatePhoto: ImageView = view.findViewById(R.id.ivCandidate) as ImageView
        val relativeLayout: RelativeLayout = view.findViewById(R.id.relative_layout)
        val programButton: Button = view.findViewById(R.id.btnProgram)
        val voteButton: Button = view.findViewById(R.id.btnVote)


    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CandidateListAdapter.MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_list_item, parent, false)

        return MyViewHolder(cell)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listElement: CandidateListElement = myDataset[position]
        holder.textView.text = myDataset[position].name
        holder.candidatePhoto.setImageResource(myDataset[position].image)

    }



    override fun getItemCount() = myDataset.size
}
