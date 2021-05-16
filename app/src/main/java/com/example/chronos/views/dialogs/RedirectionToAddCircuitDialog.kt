package com.example.chronos.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.chronos.databinding.DialogRedirectionToAddCircuitBinding

class RedirectionToAddCircuitDialog : DialogFragment()
{
    companion object {
        const val TAG: String = "RedirectionToAddCircuitDialog"
    }
    private var _binding: DialogRedirectionToAddCircuitBinding? = null
    private val binding get() = _binding!!
    var onRedirectClick: (() -> Unit)? = null
    var onCancelClick: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        _binding = DialogRedirectionToAddCircuitBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        init()
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        return dialog
    }

    override fun onDestroy()
    {
        _binding = null
        super.onDestroy()
    }

    private fun init()
    {
        binding.buttonRedirect.setOnClickListener {
            dismiss()
            onRedirectClick?.invoke()
        }
    }

    override fun onCancel(dialog: DialogInterface)
    {
        onCancelClick?.invoke()
        super.onCancel(dialog)
    }
}
