package com.example.chronos.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chronos.R
import com.example.chronos.databinding.FragmentTrainingBinding
import com.example.chronos.viewModels.CircuitViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.utils.DurationHelper

class TrainingFragment : Fragment(R.layout.fragment_training)
{
    companion object {
        const val TAG: String = "TrainingFragment"
    }
    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CircuitViewModel by viewModels {
        ViewModelProvider.provideCircuitViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrainingBinding.bind(view)
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
        viewModel.state.observe(viewLifecycleOwner) {
            binding.textViewState.text = it.toString()
        }
        viewModel.time.observe(viewLifecycleOwner) {
            binding.textViewTime.text = DurationHelper.format(it)
        }
        viewModel.circuits.observe(viewLifecycleOwner) { circuits ->
            binding.spinnerCircuits.adapter = ArrayAdapter(
                requireContext(), R.layout.item_circuit, R.id.text_view_title, circuits.map { it.title })
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