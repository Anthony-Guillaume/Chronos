package com.example.chronos.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chronos.R
import com.example.chronos.databinding.FragmentTrainingHistoryBinding
import com.example.chronos.viewModels.CircuitViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.adapters.CircuitHistoryAdapter

class TrainingHistoryFragment : Fragment(R.layout.fragment_training_history)
{
    companion object {
        const val TAG: String = "TrainingHistoryFragment"
    }
    private var _binding: FragmentTrainingHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CircuitViewModel by viewModels {
        ViewModelProvider.provideCircuitViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrainingHistoryBinding.bind(view)
        init()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun init()
    {
        viewModel.circuits.observe(viewLifecycleOwner) { circuits ->
            binding.recyclerViewCircuit.adapter = CircuitHistoryAdapter(circuits)
        }
    }
}
