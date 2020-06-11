package pl.edu.agh.votingapp.view.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Answer

class AddAnswersListAdapter(
    private val fragment: StepAddAnswersFragment,
    private val answerList: Set<Answer>
) : RecyclerView.Adapter<AddAnswersListAdapter.AddAnswersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAnswersViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_answers_list_item, parent, false) as View

        return AddAnswersViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: AddAnswersViewHolder, position: Int) {
        val answer: Answer = answerList.elementAt(position)

        holder.bind(answer)
        holder.deleteAnswerImageView.setOnClickListener {
            fragment.model.deleteAnswer(answer)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = answerList.size

    class AddAnswersViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val answerContentTextView: TextView =
            itemView.findViewById(R.id.answerContentTextView)
        val deleteAnswerImageView: ImageView = itemView.findViewById(R.id.deleteAnswerImageView)

        fun bind(answer: Answer) {
            answerContentTextView.text = answer.answerContent
        }
    }

}