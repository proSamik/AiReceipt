package com.example.aireceipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aireceipt.databinding.FragmentEditItemBinding
import com.example.aireceipt.room.Item
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditItem : BottomSheetDialogFragment() {

    private val navigationArgs: EditItemArgs by navArgs()
    lateinit var item:Item

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }
    private var _binding:FragmentEditItemBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentEditItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id=navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selectedItem->
            item=selectedItem
            bind(item)
        }
    }
    private fun bind(item:Item){
        binding.apply {
            name.setText(item.itemName, TextView.BufferType.SPANNABLE)
            price.setText(item.itemPrice.toString(),TextView.BufferType.SPANNABLE)
            quantity.setText(item.itemQuantity.toString(),TextView.BufferType.SPANNABLE)
            updateBtn.setOnClickListener { editItem() }
            deleteBtn.setOnClickListener { showCOnfirmationDialog() }
        }
    }
    private fun deleteItem(){
        viewModel.deleteItem(item)
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
            viewModel.updateItem(
                id = navigationArgs.itemId,
                binding.name.text.toString(),
                binding.price.text.toString(),
                binding.quantity.text.toString()
            )
            findNavController().navigate(R.id.action_editItem_to_inventory)
        }
    }

}