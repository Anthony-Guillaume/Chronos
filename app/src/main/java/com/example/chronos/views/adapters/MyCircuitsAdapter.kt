package com.example.chronos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chronos.data.entities.Circuit
import com.example.chronos.databinding.ItemMyCircuitsBinding

class MyCircuitsAdapter(private val dataList: List<Circuit>,
                        private val modifyListener: (Int) -> Unit,
                        private val deleteListener: (Int) -> Unit)
    : RecyclerView.Adapter<MyCircuitsAdapter.ViewHolder>()
{
    companion object {
        const val TAG: String = "MyCircuitsAdapter"
    }

    class ViewHolder(val binding: ItemMyCircuitsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(
            ItemMyCircuitsBinding.inflate(
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
        holder.binding.imageViewModify.setOnClickListener { modifyListener(holder.bindingAdapterPosition) }
        holder.binding.imageViewDelete.setOnClickListener { deleteListener(holder.bindingAdapterPosition) }
    }

    override fun getItemCount(): Int = dataList.size
}