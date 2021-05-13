package com.example.chronos.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chronos.R
import com.example.chronos.databinding.FragmentTrainingSettingBinding
import com.example.chronos.viewModels.TrainingSettingViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.dialogs.InvalidTitleDialog
import com.example.chronos.views.utils.DurationHelper
import com.google.android.material.snackbar.Snackbar

class TrainingSettingFragment : Fragment(R.layout.fragment_training_setting)
{
    companion object {
        const val TAG: String = "TrainingSettingFragment"
    }
    private var _binding: FragmentTrainingSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrainingSettingViewModel by viewModels {
        ViewModelProvider.provideTrainingSettingViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrainingSettingBinding.bind(view)
        observeViewModel()
        setActionOnViewModel()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel()
    {
        viewModel.circuit.observe(viewLifecycleOwner) {
            binding.textViewRoundNumber.text = getString(R.string.number_of_rounds_text_view, it.exercise.numberOfRound)
            binding.textViewSetNumber.text = getString(R.string.number_of_sets_text_view, it.numberOfSet)
            binding.textViewWarmup.text = getString(R.string.warmup_text_view, DurationHelper.format(it.warmup))
            binding.textViewWorkout.text = getString(R.string.workout_text_view, DurationHelper.format(it.exercise.workout))
            binding.textViewExerciceRest.text = getString(R.string.exercise_rest_text_view, DurationHelper.format(it.exercise.rest))
            binding.textViewSetRest.text = getString(R.string.rest_between_set_text_view, DurationHelper.format(it.restBetweenSet))

            binding.textViewRoundNumber.gravity = Gravity.CENTER
            binding.textViewSetNumber.gravity = Gravity.CENTER
            binding.textViewWarmup.gravity = Gravity.CENTER
            binding.textViewWorkout.gravity = Gravity.CENTER
            binding.textViewExerciceRest.gravity = Gravity.CENTER
            binding.textViewSetRest.gravity = Gravity.CENTER
        }
    }

    private fun setActionOnViewModel()
    {
        binding.textFieldTitle.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {
                s?.toString()?.let { viewModel.updateTitle(it) }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.buttonSave.setOnClickListener {
            if (binding.textFieldTitle.editText?.text.isNullOrEmpty())
            {
                InvalidTitleDialog().show(childFragmentManager, TAG)
            }
            else
            {
                viewModel.save()
                Snackbar.make(requireView(), "Circuit saved", Snackbar.LENGTH_SHORT)
                    .setAction("Undo") {
                        viewModel.deleteLastSave()
                    }
                    .show()
            }
        }
        binding.buttonWarmupMinus.setOnClickListener {
            viewModel.decrementWarmupDuration()
        }
        binding.buttonWarmupPlus.setOnClickListener {
            viewModel.incrementWarmupDuration()
        }
        binding.buttonWorkoutMinus.setOnClickListener {
            viewModel.decrementWorkoutDuration()
        }
        binding.buttonWorkoutPlus.setOnClickListener {
            viewModel.incrementWorkoutDuration()
        }
        binding.buttonExerciceRestMinus.setOnClickListener {
            viewModel.decrementExerciseRestDuration()
        }
        binding.buttonExerciceRestPlus.setOnClickListener {
            viewModel.incrementExerciseRestDuration()
        }
        binding.buttonSetRestMinus.setOnClickListener {
            viewModel.decrementSetRestDuration()
        }
        binding.buttonSetRestPlus.setOnClickListener {
            viewModel.incrementSetRestDuration()
        }
        binding.buttonRoundMinus.setOnClickListener {
            viewModel.decrementRound()
        }
        binding.buttonRoundPlus.setOnClickListener {
            viewModel.incrementRound()
        }
        binding.buttonSetMinus.setOnClickListener {
            viewModel.decrementSet()
        }
        binding.buttonSetPlus.setOnClickListener {
            viewModel.incrementSet()
        }
    }
}