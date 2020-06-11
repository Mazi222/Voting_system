package pl.edu.agh.votingapp.view.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_step_add_answers.*
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.viewmodel.create.CreateVotingViewModel

class StepAddAnswersFragment : Fragment(R.layout.fragment_step_add_answers), Step {

    private val TAG = "StepAddAnswersFragment"

    val model: CreateVotingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_add_answers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment: StepAddAnswersFragment = this
        voting_answers.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AddAnswersListAdapter(fragment, model.answers)
        }

        val addAnswerBtn: Button = view.findViewById(R.id.addAnswer)
        addAnswerBtn.setOnClickListener {
            Log.d(TAG, "Add new answer button clicked")
            val editAnswerDialog = AddAnswerDialog(voting_answers.adapter)
            editAnswerDialog.show(this.childFragmentManager, "EditAnswerDialog")
        }
    }

    override fun verifyStep(): VerificationError? {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        if (model.answers.size < 2) {
            return VerificationError("Add at least two answers")
        }
        return null
    }

    override fun onSelected() {
        //update UI when selected
    }

    override fun onError(error: VerificationError) {
    }

}
