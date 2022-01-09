package com.example.aireceipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.aireceipt.databinding.FragmentPrevBillBinding


class prev_bill : DialogFragment() {

    private var _binding: FragmentPrevBillBinding?=null
    private val binding get()=_binding!!
    private val navigationArgs:prev_billArgs by navArgs()

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPrevBillBinding.inflate(inflater,container,false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveBillImage(navigationArgs.id).observe(this.viewLifecycleOwner){
            binding.bill.setImageBitmap(it.bill)
        }
    }


}