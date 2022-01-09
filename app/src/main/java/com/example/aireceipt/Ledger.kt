package com.example.aireceipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aireceipt.adapters.AllBillsAdapter
import com.example.aireceipt.databinding.FragmentLedgerBinding


class Ledger : Fragment() {
    private var _binding:FragmentLedgerBinding?=null
    private val binding get()=_binding!!

    private val adapter by lazy{AllBillsAdapter()}

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentLedgerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.billnoRecycler.adapter=adapter
        binding.billnoRecycler.layoutManager=LinearLayoutManager(this.context)
        viewModel.allBills.observe(this.viewLifecycleOwner){
            adapter.setData(it)
        }
        binding.inventoryBtn.setOnClickListener {
            findNavController().navigate(R.id.action_ledger_to_inventory)
        }

    }


}