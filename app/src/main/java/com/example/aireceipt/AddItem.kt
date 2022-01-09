package com.example.aireceipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.aireceipt.databinding.FragmentAddItemBinding
import com.example.aireceipt.room.Item
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddItem : BottomSheetDialogFragment() {
    private val viewModel:InventoryViewModel by activityViewModels{
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database
                .itemDao()
        )
    }
    lateinit var item: Item
    private var _binding:FragmentAddItemBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentAddItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBtn.setOnClickListener {
            addNewItem()
        }
    }

    private fun addNewItem(){
        if(isEntryValid()){
            viewModel.addNewItem(
                binding.name.text.toString(),
                binding.price.text.toString(),
                binding.quantity.text.toString()
            )
            findNavController().navigate(R.id.action_addItem_to_inventory)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.name.text.toString(),
            binding.price.text.toString(),
            binding.quantity.text.toString()
        )
    }

}