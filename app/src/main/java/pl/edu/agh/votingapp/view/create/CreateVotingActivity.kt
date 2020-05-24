package pl.edu.agh.votingapp.view.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import pl.edu.agh.votingapp.R

class CreateVotingActivity : AppCompatActivity(), StepperLayout.StepperListener {

    private var mStepperLayout: StepperLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_voting)

        mStepperLayout = findViewById<View>(R.id.stepperLayout) as StepperLayout
        mStepperLayout!!.adapter = CreateVotingStepperAdapter(
            supportFragmentManager,
            this
        )
        mStepperLayout!!.setListener(this)
    }

    override fun onCompleted(completeButton: View?) {
    }

    override fun onError(verificationError: VerificationError) {
        Toast.makeText(
            this,
            "Error: " + verificationError.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStepSelected(newStepPosition: Int) {
    }

    override fun onReturn() {
        finish()
    }
}
