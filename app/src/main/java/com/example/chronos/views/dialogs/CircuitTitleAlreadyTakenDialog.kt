package com.example.chronos.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.chronos.databinding.DialogCircuitTitleAlreadyTakenBinding

class CircuitTitleAlreadyTakenDialog : DialogFragment()
{
    companion object {
        const val TAG: String = "CircuitTitleAlreadyTakenDialog"
    }
    private var _binding: DialogCircuitTitleAlreadyTakenBinding? = null
    private val binding get() = _binding!!
    var onOverrideClick: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        _binding = DialogCircuitTitleAlreadyTakenBinding.inflate(LayoutInflater.from(context))
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
        binding.buttonOverride.setOnClickListener {
            dismiss()
            onOverrideClick?.invoke()
        }
    }
}
