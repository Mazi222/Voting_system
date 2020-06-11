package pl.edu.agh.votingapp.view.vote

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R

class CandidateListAdapter(private val myDataset: List<AnswerListElement>) :
    RecyclerView.Adapter<CandidateListAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View, val parent: ViewGroup) : RecyclerView.ViewHolder(view) {

        val candidateName: TextView = view.findViewById(R.id.candidate_name) as TextView
        val candidatePhoto: ImageView = view.findViewById(R.id.ivCandidatePhoto) as ImageView
        val relativeLayout: RelativeLayout = view.findViewById(R.id.relative_layout)
        val programButton: Button = view.findViewById(R.id.btnProgram)
        val voteButton: Button = view.findViewById(R.id.btnVote)

        fun bindButtons() {
            programButton.setOnClickListener {
                val intent = Intent(parent.context, CandidateProgramActivity::class.java)
                parent.context.startActivity(intent)
            }
            voteButton.setOnClickListener {
                val intent = Intent(parent.context, CandidateVoteActivity::class.java)
                intent.putExtra("name", candidateName.text)
                parent.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_list_item, parent, false)

        return MyViewHolder(cell, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listElement: AnswerListElement = myDataset[position]
        holder.candidateName.text = myDataset[position].name
        holder.candidatePhoto.setImageResource(myDataset[position].image)

        holder.bindButtons()

    }

    override fun getItemCount() = myDataset.size
}
