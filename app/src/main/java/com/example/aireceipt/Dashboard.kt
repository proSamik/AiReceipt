package com.example.aireceipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aireceipt.databinding.FragmentDashboardBinding

class Dashboard : Fragment() {

    private var _binding:FragmentDashboardBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inventoryBtn.setOnClickListener{
            findNavController().navigate(R.id.action_dashboard_to_inventory)
        }
        binding.prevBillBtn.setOnClickListener{
            findNavController().navigate(R.id.action_dashboard_to_ledger)
        }
    }


}