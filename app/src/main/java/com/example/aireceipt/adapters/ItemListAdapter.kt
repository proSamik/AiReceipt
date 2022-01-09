package com.example.aireceipt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aireceipt.databinding.InventoryListBinding
import com.example.aireceipt.room.Item

class ItemListAdapter(private val onItemClicked:(Item)->Unit):
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            InventoryListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }
        holder.bind(current)

    }

    class ItemViewHolder(private var binding:InventoryListBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item){
            binding.apply {
                itemName.text=item.itemName
                itemPrice.text=item.itemPrice.toString()
                itemQuantity.text=item.itemQuantity.toString()
            }

        }
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.itemName == newItem.itemName
            }
        }
    }
}