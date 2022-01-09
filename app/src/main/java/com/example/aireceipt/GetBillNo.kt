package com.example.aireceipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aireceipt.databinding.FragmentGetBillNoBinding


class GetBillNo : Fragment() {

    private var _binding:FragmentGetBillNoBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentGetBillNoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener{
            val billno=binding.billno.text.toString()
            if(!billno.isEmpty()) {
                val action = GetBillNoDirections.actionGetBillNoToBillCheckout(billno = billno)
                this.findNavController().navigate(action)
            }
        }

    }


}