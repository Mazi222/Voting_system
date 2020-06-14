package pl.edu.agh.votingapp.view.create

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.viewmodel.create.CreateVotingViewModel

class AddAnswerDialog(
    private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?
) : DialogFragment() {

    private val TAG = "AddAnswerDialog"

    val model: CreateVotingViewModel by activityViewModels()

    private lateinit var answerPictureImageView: ImageView
    private lateinit var addPictureBtn: Button
    private lateinit var answerContentLayout: TextInputLayout
    private lateinit var addAnswerBtn: Button

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setTitle("Add new answer")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_add_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answerPictureImageView = view.findViewById(R.id.answerPictureImageView)
        addPictureBtn = view.findViewById(R.id.addPictureBtn)
        answerContentLayout = view.findViewById(R.id.answerContentAddLayout)
        addAnswerBtn = view.findViewById(R.id.addAnswerBtn)

        answerPictureImageView.isVisible = false

        Handler().postDelayed({
            answerContentLayout.requestFocus()
            val imm: InputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(answerContentLayout, InputMethodManager.SHOW_IMPLICIT)
        }, 200)

        addAnswerBtn.setOnClickListener {
            Log.d(TAG, "Add answer button clicked in dialog")
            val answerText: String = answerContentLayout.editText?.text.toString()
            if (answerText.isBlank()) {
                Toast.makeText(
                    context,
                    "Answer description cannot be empty!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newAnswer = Answer()
                newAnswer.answerContent = answerText
                model.addAnswer(newAnswer)
                adapter?.notifyDataSetChanged()

                dialog?.dismiss()
            }
        }
    }
}