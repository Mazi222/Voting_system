package pl.edu.agh.votingapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity

class CandidateListAdapter(private val myDataset: Array<CandidateListElement>) :
    RecyclerView.Adapter<CandidateListAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View, val parent: ViewGroup) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.candidate_name) as TextView
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
                val intent = Intent(parent.context, VoteActivity::class.java)
                parent.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CandidateListAdapter.MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_list_item, parent, false)

        return MyViewHolder(cell, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listElement: CandidateListElement = myDataset[position]
        holder.textView.text = myDataset[position].name
        holder.candidatePhoto.setImageResource(myDataset[position].image)

        holder.bindButtons()

    }

    override fun getItemCount() = myDataset.size
}
