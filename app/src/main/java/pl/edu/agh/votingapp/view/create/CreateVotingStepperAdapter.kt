package pl.edu.agh.votingapp.view.create

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel


class CreateVotingStepperAdapter(fm: FragmentManager, context: Context) :
    AbstractFragmentStepAdapter(fm, context) {

    private val CURRENT_STEP_POSITION_KEY = "messageResourceId"

    override fun createStep(position: Int): Step {
        val step = StepChooseTypeFragment()
        val b = Bundle()
        b.putInt(CURRENT_STEP_POSITION_KEY, position)
        step.arguments = b
        return step
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getViewModel(position: Int): StepViewModel {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return StepViewModel.Builder(context)
//            .setTitle(R.string.tab_title) //can be a CharSequence instead
            .setTitle("Choose type")
            .create()
    }
}