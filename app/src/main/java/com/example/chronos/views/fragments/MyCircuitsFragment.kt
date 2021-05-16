package com.example.chronos.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chronos.R
import com.example.chronos.databinding.FragmentMyCircuitsBinding
import com.example.chronos.viewModels.MyCircuitsViewModel
import com.example.chronos.viewModels.ViewModelProvider
import com.example.chronos.views.adapters.MyCircuitsAdapter

class MyCircuitsFragment : Fragment(R.layout.fragment_my_circuits) {
    companion object {
        const val TAG: String = "MyCircuitsFragment"
    }

    private var _binding: FragmentMyCircuitsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyCircuitsViewModel by viewModels {
        ViewModelProvider.provideMyCircuitsViewModelFactory(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyCircuitsBinding.bind(view)
        observeViewModel()
        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_myCircuitsFragment_to_createCircuitFragment)
        }
        viewModel.fetchData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onResume()
    {
        binding.recyclerViewCircuit.adapter?.notifyDataSetChanged()
        super.onResume()
    }

    private fun observeViewModel()
    {
        viewModel.circuits.observe(viewLifecycleOwner) {
            binding.recyclerViewCircuit.adapter = MyCircuitsAdapter(it, ::onClickModify, ::onClickDelete)
        }
    }

    private fun onClickModify(position: Int)
    {
        viewModel.setCircuitToEdit(position)
        findNavController().navigate(R.id.action_myCircuitsFragment_to_editMyCircuitFragment)
    }

    private fun onClickDelete(position: Int)
    {
        viewModel.deleteCircuit(position)
        binding.recyclerViewCircuit.adapter?.notifyItemRemoved(position)
    }
}