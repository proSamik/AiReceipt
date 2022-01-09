package com.example.aireceipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aireceipt.adapters.ItemListAdapter
import com.example.aireceipt.databinding.FragmentInventoryBinding


class Inventory : Fragment() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding:FragmentInventoryBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentInventoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemListAdapter{
            val action=InventoryDirections.actionInventoryToEditItem(it.id)
            this.findNavController().navigate(action)
        }
        binding.inventoryRecycler.adapter=adapter
        viewModel.allItems.observe(this.viewLifecycleOwner) { items->
            items.let{
                adapter.submitList(it)
            }
        }
        binding.inventoryRecycler.layoutManager=LinearLayoutManager(this.context)
        binding.addNewItembtn.setOnClickListener{
            findNavController().navigate(R.id.action_inventory_to_addItem)
        }
    }

}