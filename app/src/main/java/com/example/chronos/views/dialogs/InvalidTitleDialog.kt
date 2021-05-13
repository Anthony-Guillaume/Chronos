package com.example.chronos.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.chronos.databinding.DialogInvalidTitleBinding

class InvalidTitleDialog : DialogFragment()
{
    companion object {
        const val TAG: String = "InvalidTitleDialog"
    }
    private var _binding: DialogInvalidTitleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        _binding = DialogInvalidTitleBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        init()
        return builder.create()
    }

    override fun onDestroy()
    {
        _binding = null
        super.onDestroy()
    }

    private fun init()
    {
        binding.buttonClose.setOnClickListener { dismiss() }
    }
}
