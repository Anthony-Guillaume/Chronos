package com.example.chronos.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chronos.R
import com.example.chronos.data.models.CircuitHistory
import com.example.chronos.databinding.ItemCircuitHistoryBinding
import com.example.chronos.views.utils.DateHelper

class CircuitHistoryAdapter(private val dataList: List<CircuitHistory>)
    : RecyclerView.Adapter<CircuitHistoryAdapter.ViewHolder>()
{
    companion object {
        const val TAG: String = "CircuitHistoryAdapter"
    }

    class ViewHolder(val binding: ItemCircuitHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(
            ItemCircuitHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = dataList[position]
        val context: Context = holder.itemView.context
        holder.binding.textViewDate.text = DateHelper.format(item.date)
        holder.binding.textViewTitle.text = item.title
        holder.binding.textViewNumberOfSet.text = context.getString(
            R.string.ratio_number_of_sets_done, item.numberOfSetsDone, item.numberOfSets)
    }

    override fun getItemCount(): Int = dataList.size
}