package com.example.chronos.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chronos.R
import com.example.chronos.data.entities.State
import com.example.chronos.databinding.FragmentChronoTrainingBinding
import com.example.chronos.viewModels.TrainingChronoViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.dialogs.RedirectionToAddCircuitDialog
import com.example.chronos.views.utils.DurationHelper

class TrainingChronoFragment : Fragment(R.layout.fragment_chrono_training)
{
    companion object {
        const val TAG: String = "TrainingFragment"
    }
    private var _binding: FragmentChronoTrainingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrainingChronoViewModel by viewModels {
        ViewModelProvider.provideTrainingChronoViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChronoTrainingBinding.bind(view)
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
        viewModel.needToCreateCircuit.observe(viewLifecycleOwner) {
            if (it)
            {
                val dialog = RedirectionToAddCircuitDialog()
                dialog.onRedirectClick = { findNavController().navigate(R.id.action_trainingChronoFragment_to_createCircuitFragment) }
                dialog.onCancelClick = { findNavController().navigate(R.id.action_trainingChronoFragment_to_homeFragment) }
                dialog.show(childFragmentManager, TAG)
            }
        }
        viewModel.time.observe(viewLifecycleOwner) {
            binding.textViewTime.text = DurationHelper.format(it)
        }
        viewModel.circuits.observe(viewLifecycleOwner) { circuits ->
            binding.spinnerCircuits.adapter = ArrayAdapter(
                requireContext(), R.layout.item_circuit, R.id.text_view_title, circuits.map { it.title })
        }
        viewModel.canStart.observe(viewLifecycleOwner) {
            binding.buttonStop.isEnabled = !it
            binding.buttonReset.isEnabled = !it
            binding.buttonStart.isEnabled = it
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it)
            {
                State.Warmup -> binding.textViewState.text = getString(R.string.state_warmup)
                State.Workout -> binding.textViewState.text = getString(R.string.state_workout)
                State.ExerciseResting -> binding.textViewState.text = getString(R.string.state_exerciseResting)
                State.SetResting -> binding.textViewState.text = getString(R.string.state_setResting)
                State.Done -> binding.textViewState.text = getString(R.string.state_done)
                else -> {}
            }
        }
    }

    private fun setActionOnViewModel()
    {
        binding.buttonReset.setOnClickListener {
            viewModel.reset()
        }
        binding.buttonStart.setOnClickListener {
            viewModel.start()
        }
        binding.buttonStop.setOnClickListener {
            viewModel.stop()
        }
        binding.spinnerCircuits.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                parent?.let { viewModel.updateCircuitSelected(position) }
            }
        }
    }
}