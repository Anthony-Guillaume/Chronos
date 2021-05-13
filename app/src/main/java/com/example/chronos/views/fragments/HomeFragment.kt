package com.example.chronos.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chronos.R
import com.example.chronos.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home)
{
    companion object {
        const val TAG: String = "HomeFragment"
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        init()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun init()
    {
        binding.buttonTraining.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_trainingFragment)
        }
        binding.buttonTrainingHistory.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_trainingHistoryFragment)
        }
        binding.buttonTrainingSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_trainingSettingFragment)
        }
    }
}