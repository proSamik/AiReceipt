package com.example.aireceipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aireceipt.databinding.FragmentEditBillItemBinding
import com.example.aireceipt.room.Bill
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_get_bill_no.*


class EditBillItem : BottomSheetDialogFragment() {

    private val navigationArgs: EditBillItemArgs by navArgs()
    lateinit var bill:Bill
    private val viewModel:InventoryViewModel by activityViewModels{
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database
                .itemDao()
        )
    }
    private var _binding: FragmentEditBillItemBinding?=null
    private val binding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEditBillItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id=navigationArgs.itemId
        viewModel.retrieveBill(id.toInt()).observe(this.viewLifecycleOwner){selectedItem->
            bill=selectedItem
            bind(bill)
        }
    }
    private fun bind(bill: Bill){
        binding.apply {
            name.setText(bill.itemName, TextView.BufferType.SPANNABLE)
            price.setText(bill.itemPrice.toString(), TextView.BufferType.SPANNABLE)
            quantity.setText(bill.itemQuantity.toString(), TextView.BufferType.SPANNABLE)
            updateBtn.setOnClickListener { editItem() }
            deleteBtn.setOnClickListener { showCOnfirmationDialog() }
        }
    }
    private fun deleteItem(){
        viewModel.deleteBillItem(bill)
        findNavController().navigateUp()
    }
    private fun showCOnfirmationDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) {_, _ ->}
            .setPositiveButton(getString(R.string.yes)) {_, _ -> deleteItem()}
            .show()
    }
    private fun isEntryValid():Boolean{
        return viewModel.isEntryValid(
            binding.name.text.toString(),
            binding.price.text.toString(),
            binding.quantity.text.toString()
        )
    }
    private fun editItem(){
        if(isEntryValid()){
            viewModel.updateBillItem(
                billno = navigationArgs.billno,
                id = navigationArgs.itemId.toInt(),
                binding.name.text.toString(),
                binding.price.text.toString(),
                binding.quantity.text.toString()
            )
            findNavController().navigateUp()
        }
    }

}