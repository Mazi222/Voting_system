package pl.edu.agh.votingapp.view.vote

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.comunication.model.UserDto

class FirstPastThePostAdapter(private val myDataset: List<AnswerListElement>, val mContext: Context) :
    RecyclerView.Adapter<FirstPastThePostAdapter.MyViewHolder>() {

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
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_list_item, parent, false)

        return MyViewHolder(cell, parent)
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listElement: AnswerListElement = myDataset[position]
        holder.candidateName.text = listElement.name
        holder.candidatePhoto.setImageResource(listElement.image)

        holder.bindButtons()

        holder.itemView.setOnClickListener {
            Log.d("Holder", "Click")
            addAnswerToMap(position)
        }
    }

    fun addAnswerToMap(position: Int) {
        val activity = mContext as AnswerListActivity
        activity.updateAnswerMap(myDataset[position].answerId, 1)
    }
}
