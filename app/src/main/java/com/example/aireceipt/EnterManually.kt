package com.example.aireceipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aireceipt.databinding.FragmentEnterManuallyBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EnterManually : BottomSheetDialogFragment() {

    private val navigationArgs: EnterManuallyArgs by navArgs()
    private val viewModel:InventoryViewModel by activityViewModels{
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database
                .itemDao()
        )
    }
    private var _binding:FragmentEnterManuallyBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentEnterManuallyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.setText(navigationArgs.itemname)
        binding.addBtn.setOnClickListener{
            addNewItem()
        }
    }

    private fun addNewItem() {
        if(isEntryValid()){
            viewModel.addNewBillItem(
                navigationArgs.itemid,
                binding.name.text.toString(),
                binding.price.text.toString(),
                binding.quantity.text.toString()
            )
            val action = EnterManuallyDirections.actionEnterManuallyToBillCheckout(navigationArgs.itemid)
            findNavController().navigate(action)
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