package com.example.chronos.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chronos.R
import com.example.chronos.databinding.FragmentCreateCircuitBinding
import com.example.chronos.viewModels.CreateCircuitViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.dialogs.CircuitTitleAlreadyTakenDialog
import com.example.chronos.views.dialogs.InvalidTitleDialog
import com.example.chronos.views.utils.ContinuousClickHandler
import com.example.chronos.views.utils.DurationHelper
import com.google.android.material.snackbar.Snackbar

class CreateCircuitFragment : Fragment(R.layout.fragment_create_circuit)
{
    companion object {
        const val TAG: String = "CreateCircuitFragment"
    }
    private var _binding: FragmentCreateCircuitBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateCircuitViewModel by viewModels {
        ViewModelProvider.provideCreateCircuitViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateCircuitBinding.bind(view)
        observeViewModel()
        setActionOnViewModel()
        viewModel.fetchData()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel()
    {
        viewModel.circuit.observe(viewLifecycleOwner) {
            binding.textViewRoundNumber.text = getString(R.string.number_of_rounds_setting, it.exercise.numberOfRounds)
            binding.textViewSetNumber.text = getString(R.string.number_of_sets_setting, it.numberOfSets)
            binding.textViewWarmup.text = getString(R.string.warmup_setting, DurationHelper.format(it.warmup))
            binding.textViewWorkout.text = getString(R.string.workout_setting, DurationHelper.format(it.exercise.workout))
            binding.textViewExerciceRest.text = getString(R.string.exercise_rest_setting, DurationHelper.format(it.exercise.rest))
            binding.textViewSetRest.text = getString(R.string.rest_between_sets_setting, DurationHelper.format(it.restBetweenSets))

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
            else if (viewModel.isAlreadyCircuitWithTitle())
            {
                val dialog = CircuitTitleAlreadyTakenDialog()
                dialog.onOverrideClick = { viewModel.save() }
                dialog.show(childFragmentManager, TAG)
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
        ContinuousClickHandler(binding.buttonWarmupMinus) { viewModel.decrementWarmupDuration() }
        ContinuousClickHandler(binding.buttonWarmupPlus) { viewModel.incrementWarmupDuration() }
        ContinuousClickHandler(binding.buttonWorkoutMinus) { viewModel.decrementWorkoutDuration() }
        ContinuousClickHandler(binding.buttonWorkoutPlus) { viewModel.incrementWorkoutDuration() }
        ContinuousClickHandler(binding.buttonExerciceRestMinus) { viewModel.decrementExerciseRestDuration() }
        ContinuousClickHandler(binding.buttonExerciceRestPlus) { viewModel.incrementExerciseRestDuration() }
        ContinuousClickHandler(binding.buttonSetRestMinus) { viewModel.decrementSetRestDuration() }
        ContinuousClickHandler(binding.buttonSetRestPlus) { viewModel.incrementSetRestDuration() }
        ContinuousClickHandler(binding.buttonRoundMinus) { viewModel.decrementRound() }
        ContinuousClickHandler(binding.buttonRoundPlus) { viewModel.incrementRound() }
        ContinuousClickHandler(binding.buttonSetMinus) { viewModel.decrementSet() }
        ContinuousClickHandler(binding.buttonSetPlus) { viewModel.incrementSet() }
    }
}