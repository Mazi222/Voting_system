package pl.edu.agh.votingapp.view.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Answers

class AddAnswersListAdapter(
    private val fragment: StepAddAnswersFragment,
    private val answersList: List<Answers>
) : RecyclerView.Adapter<AddAnswersListAdapter.AddAnswersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAnswersViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_answers_list_item, parent, false) as View

        return AddAnswersViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: AddAnswersViewHolder, position: Int) {
        val answers: Answers = answersList[position]

        holder.bind(answers)
    }

    override fun getItemCount() = answersList.size

    class AddAnswersViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)

        fun bind(answers: Answers) {
            contentTextView.text = answers.answerContent
        }
    }

}