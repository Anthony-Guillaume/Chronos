package com.example.chronos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chronos.data.models.Circuit
import com.example.chronos.databinding.ItemCircuitHistoryBinding

class CircuitHistoryAdapter(private val dataList: List<Circuit>) : RecyclerView.Adapter<CircuitHistoryAdapter.ViewHolder>()
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
        holder.binding.textViewTitle.text = item.title
        holder.binding.textViewNumberOfSet.text = item.numberOfSet.toString()
        holder.binding.textViewNumberOfRound.text = item.exercise.numberOfRound.toString()
    }

    override fun getItemCount(): Int = dataList.size
}