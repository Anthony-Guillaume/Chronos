package com.example.chronos.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chronos.R
import com.example.chronos.databinding.FragmentTrainingHistoryBinding
import com.example.chronos.viewModels.TrainingHistoryViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.adapters.CircuitHistoryAdapter

class TrainingHistoryFragment : Fragment(R.layout.fragment_training_history)
{
    companion object {
        const val TAG: String = "TrainingHistoryFragment"
    }
    private var _binding: FragmentTrainingHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrainingHistoryViewModel by viewModels {
        ViewModelProvider.provideTrainingHistoryViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrainingHistoryBinding.bind(view)
        observeViewModel()
        viewModel.fetchData()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel()
    {
        viewModel.histories.observe(viewLifecycleOwner) {
            binding.recyclerViewCircuit.adapter = CircuitHistoryAdapter(it)
        }
    }
}
