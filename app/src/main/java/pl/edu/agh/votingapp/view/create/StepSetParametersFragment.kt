package pl.edu.agh.votingapp.view.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.viewmodel.create.CreateVotingViewModel
import java.util.*


class StepSetParametersFragment : Fragment(R.layout.fragment_step_set_parameters), Step {

    val model: CreateVotingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_set_parameters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val votingDesc: TextInputEditText = view.findViewById(R.id.votingDesc)
        val pplToChoose: TextInputEditText = view.findViewById(R.id.pplToChoose)
        val pplEntitled: TextInputEditText = view.findViewById(R.id.pplEntitled)
        val quorum: TextInputEditText = view.findViewById(R.id.quorum)
        val openGroup: RadioGroup = view.findViewById(R.id.openGroup)
        val endDate: TextInputEditText = view.findViewById(R.id.endDate)

        votingDesc.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) model.content = s.toString()
            }
        })

        pplToChoose.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) model.numOfPeopleToChoose = s.toString().toInt()
            }
        })

        pplEntitled.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) model.numOfPeopleEntitled = s.toString().toInt()
            }
        })

        quorum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) model.quorum = s.toString().toInt()
            }
        })

        openGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.openTrue -> {
                    model.isOpen = true
                }
                R.id.openFalse -> {
                    model.isOpen = false
                }
                else -> {
                }
            }
        }

        endDate.setOnClickListener {
            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
            val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentDateTime.get(Calendar.MINUTE)

            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    TimePickerDialog(
                        requireContext(),
                        TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                            val pickedDateTime = Calendar.getInstance()
                            pickedDateTime.set(year, month, day, hour, minute)
                            model.endTime = pickedDateTime.time
                        },
                        startHour,
                        startMinute,
                        true
                    ).show()
                },
                startYear,
                startMonth,
                startDay
            ).show()
        }

    }

    override fun verifyStep(): VerificationError? {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null
    }

    override fun onSelected() {
        //update UI when selected
    }

    override fun onError(error: VerificationError) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

}
