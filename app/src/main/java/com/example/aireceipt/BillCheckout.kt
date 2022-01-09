package com.example.aireceipt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aireceipt.adapters.BillListAdapter
import com.example.aireceipt.databinding.FragmentBillCheckoutBinding
import kotlinx.android.synthetic.main.inventory_list.*
import java.io.ByteArrayOutputStream

class BillCheckout : Fragment() {
    val TAG = "cbill"
    val navigationArgs: BillCheckoutArgs by navArgs()
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding:FragmentBillCheckoutBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentBillCheckoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bno = navigationArgs.billno.toString().toInt()
        binding.billno.text=bno.toString()
        Log.i("cbill", "onViewCreated: $bno")
        val adapter = BillListAdapter{
                val actionx = BillCheckoutDirections.actionBillCheckoutToEditBillItem(it.id.toString(),it.billNo.toString())
                this.findNavController().navigate(actionx)
        }
        binding.billRecycler.adapter=adapter
      //  Log.i("cbill", "onViewCreated: ")

       // Log.i("cbill","nahi chala yaha se")
            viewModel.currentBill(bno).observe(this.viewLifecycleOwner) { items ->
                    items.let {
                        adapter.submitList(it)
                    }
                }
        binding.billRecycler.layoutManager=LinearLayoutManager(this.context)

        binding.addbillnewitembtn.setOnClickListener {
            if(viewModel.x != null){
                val action=BillCheckoutDirections.actionBillCheckoutToEnterManually(itemid = binding.billno.text.toString(), itemname = viewModel.x.toString())
                  findNavController().navigate(action)

            }
            else {
                val action = BillCheckoutDirections.actionBillCheckoutToEnterManually(
                    itemid = binding.billno.text.toString(),
                    itemname = ""
                )
                findNavController().navigate(action)
            }
            }
        //share
        binding.shareBtn.setOnClickListener{
            var bit=getScreenShotFromView(binding.billRecycler)
            Log.i(TAG, "onViewCreated: run successfull")
            if(bit!=null){
                share(bit)
            }
            else{
                Log.i("cbill", "could'nt capture ")
            }
        }



    }
        private fun getScreenShotFromView(V: View):Bitmap?{
            Log.i(TAG, "getScreenShotFromView:ran ")
            var screenshot:Bitmap?=null
            try{
                screenshot= Bitmap.createBitmap(V.measuredWidth, V.measuredHeight, Bitmap.Config.ARGB_8888 )
                val canvas =Canvas(screenshot)
                V.draw(canvas)
            }
            catch (e:Exception) {
                Log.e("cbill","Failed to capure")
            }
            return screenshot
        }

    private fun share(bitmap: Bitmap){
       /* val filename="${System.currentTimeMillis()}.jpg"
        var fos:OutputStream?=null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            activity?.contentResolver?.also {resolver->
                val contentvalues=ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri:Uri?=resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentvalues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        }else{
            val imageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image= File(imageDir,filename)
            fos=FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,it)
        }*/

        val outputstream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputstream)
         val path=MediaStore.Images.Media.insertImage(requireContext().contentResolver,bitmap,"tempimage",null)
        val imageUri=Uri.parse(path)
        val intent=Intent(Intent.ACTION_SEND).setType("image/*").putExtra(Intent.EXTRA_STREAM,imageUri)
        viewModel.getbill(navigationArgs.billno.toInt(),bitmap)
        Log.i(TAG, "share: intent start failed")
        this.startActivity(intent)
    }

}