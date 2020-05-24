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
        when (position) {
            0 -> {
                val step1 = StepChooseTypeFragment()
                val b1 = Bundle()
                b1.putInt(CURRENT_STEP_POSITION_KEY, position)
                step1.arguments = b1
                return step1
            }
            1 -> {
                val step2 = StepSetParametersFragment()
                val b2 = Bundle()
                b2.putInt(CURRENT_STEP_POSITION_KEY, position)
                step2.arguments = b2
                return step2
            }
            2 -> {
                val step3 = StepAddAnswersFragment()
                val b3 = Bundle()
                b3.putInt(CURRENT_STEP_POSITION_KEY, position)
                step3.arguments = b3
                return step3
            }
            else -> return createStep(0)
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getViewModel(position: Int): StepViewModel {
        when (position) {
            0 -> {
                return StepViewModel.Builder(context)
//                    .setTitle(R.string.tab_title) //can be a CharSequence instead
                    .setTitle("Choose type")
                    .create()
            }
            1 -> {
                return StepViewModel.Builder(context)
                    .setTitle("Settings")
                    .create()
            }
            2 -> {
                return StepViewModel.Builder(context)
                    .setTitle("Add answers")
                    .create()
            }
            else -> return StepViewModel.Builder(context)
                .create()
        }
    }
}