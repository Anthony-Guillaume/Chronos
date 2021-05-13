package com.example.chronos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chronos.data.models.Circuit
import com.example.chronos.databinding.ItemCircuitBinding

class CircuitAdapter(private val dataList: ArrayList<Circuit>) : RecyclerView.Adapter<CircuitAdapter.ViewHolder>()
{

    class ViewHolder(val binding: ItemCircuitBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(
            ItemCircuitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.binding.textViewTitle.text = dataList[position].title
    }

    override fun getItemCount(): Int = dataList.size
}