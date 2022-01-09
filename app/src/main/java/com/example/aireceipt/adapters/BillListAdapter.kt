package com.example.aireceipt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aireceipt.databinding.InventoryListBinding
import com.example.aireceipt.room.Bill

class BillListAdapter(private val onItemClicked:(Bill) ->Unit):
ListAdapter<Bill, BillListAdapter.ItemViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BillListAdapter.ItemViewHolder {
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

    class ItemViewHolder(private var binding:InventoryListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(bill:Bill){
            binding.apply {
                itemName.text=bill.itemName
                itemPrice.text=bill.itemPrice.toString()
                itemQuantity.text=bill.itemQuantity.toString()
            }
        }

    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Bill>() {
            override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
                return oldItem.itemName == newItem.itemName
            }
        }
    }
}