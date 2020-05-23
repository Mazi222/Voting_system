package pl.edu.agh.votingapp.view.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_step_choose_type.*
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.viewmodel.create.CreateVotingViewModel


class StepChooseTypeFragment : Fragment(), Step {

    val model: CreateVotingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_step_choose_type, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment: StepChooseTypeFragment = this
        choose_type.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter =
                MyListAdapter(fragment, VotingType.values().filter { t -> t != VotingType.NONE })
        }
    }

    override fun verifyStep(): VerificationError? {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        if (!model.isVotingTypeInitialized()) {
            return VerificationError("Choose a voting type!")
        }
        return null
    }

    override fun onSelected() {
        //update UI when selected
    }

    override fun onError(error: VerificationError) {
        Toast.makeText(
            context,
            error.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    class MyListAdapter(
        private val fragment: StepChooseTypeFragment,
        private val listData: List<VotingType>
    ) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val listItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as View

            return MyViewHolder(listItem)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val votingType: VotingType = listData[position]
            holder.bind(fragment, votingType)
        }

        override fun getItemCount() = listData.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descTextView: TextView = itemView.findViewById(R.id.descTextView)

        fun bind(fragment: StepChooseTypeFragment, votingType: VotingType) {
            titleTextView.text = votingType.type
            descTextView.text = votingType.description

            itemView.setOnClickListener { view ->
                Toast.makeText(
                    view.context,
                    "Selected: " + votingType.type,
                    Toast.LENGTH_SHORT
                ).show()
                fragment.model.votingType = votingType
                itemView.isActivated = true
            }
        }
    }

}
