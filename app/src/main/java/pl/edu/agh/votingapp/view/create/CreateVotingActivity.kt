package pl.edu.agh.votingapp.view.create

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import pl.edu.agh.votingapp.MainActivity
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.viewmodel.create.CreateVotingViewModel

class CreateVotingActivity : AppCompatActivity(), StepperLayout.StepperListener {

    private val TAG = "CreateVotingActivity"

    val model: CreateVotingViewModel by viewModels()
    var mStepperLayout: StepperLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_voting)
        title = "Create voting"

        mStepperLayout = findViewById<View>(R.id.stepperLayout) as StepperLayout
        mStepperLayout!!.adapter = CreateVotingStepperAdapter(
            supportFragmentManager,
            this
        )
        mStepperLayout!!.setListener(this)
    }

    override fun onCompleted(completeButton: View?) {
        Log.d(TAG, "Create voting complete clicked")
        model.createVoting()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(
            this,
            "Successfully created new voting!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onError(verificationError: VerificationError) {
        Log.d(TAG, "Verification error: " + verificationError.errorMessage)
        Toast.makeText(
            this,
            verificationError.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStepSelected(newStepPosition: Int) {
    }

    override fun onReturn() {
        finish()
    }
}
