package pl.edu.agh.votingapp.view.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.viewmodel.create.CreateVotingViewModel
import java.sql.Date
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val votingDesc: TextInputEditText = view.findViewById(R.id.votingDesc)
        val pplToChoose: TextInputEditText = view.findViewById(R.id.pplToChoose)
        val pplEntitled: TextInputEditText = view.findViewById(R.id.pplEntitled)
        val quorum: TextInputEditText = view.findViewById(R.id.quorum)
        val openGroup: RadioGroup = view.findViewById(R.id.openGroup)
        val votingCode: TextInputEditText = view.findViewById(R.id.votingCode)
        val endDate: TextInputEditText = view.findViewById(R.id.endDate)
        endDate.showSoftInputOnFocus = false
        openGroup.findViewById<RadioButton>(R.id.openTrue).isChecked = true
        votingCode.isVisible = false

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
                    votingCode.isVisible = false
                    model.votingCode = -1L
                }
                R.id.openFalse -> {
                    model.isOpen = false
                    votingCode.isVisible = true
                }
                else -> {
                }
            }
        }

        votingCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) model.votingCode = s.toString().toLong()
            }
        })

        endDate.setOnClickListener {
            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
            val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentDateTime.get(Calendar.MINUTE)
            val pickedDateTime = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    TimePickerDialog(
                        requireContext(),
                        TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                            pickedDateTime.set(year, month, day, hour, minute)
                            model.endTime = Date(pickedDateTime.time.time)
                            endDate.setText(
                                DateFormat.format(
                                    "dd.MM.yyyy, HH:mm",
                                    pickedDateTime.time
                                ), TextView.BufferType.EDITABLE
                            )
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
        if (!model.isContentInitialized() || model.content.isBlank()) {
            return VerificationError("Fill the description field")
        }
        if (model.numOfPeopleToChoose < 1) {
            return VerificationError("Choose number of options to choose")
        }
        if (model.quorum < 1) {
            return VerificationError("Choose quorum number")
        }
        if (model.numOfPeopleEntitled!! < Int.MAX_VALUE &&
            model.numOfPeopleEntitled!! < model.quorum
        ) {
            return VerificationError("Quorum cannot be greater than the number of entitled voters")
        }
        if (!model.isEndTimeInitialized()) {
            return VerificationError("Choose end time")
        }
        if (model.endTime.before(Date())) {
            return VerificationError("End date cannot be in the past")
        }
        if (!model.isOpen && model.votingCode == -1L) {
            return VerificationError("Voting is closed, choose voting code")
        }
        return null
    }

    override fun onSelected() {
        //update UI when selected
    }

    override fun onError(error: VerificationError) {
    }

}
