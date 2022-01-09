package com.example.aireceipt

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.aireceipt.room.*
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao):ViewModel() {

    val allItems:LiveData<List<Item>> = itemDao.getItems().asLiveData()
    val allitems: LiveData<List<Item>> = itemDao.getitems().asLiveData()
    val allBills: LiveData<List<AllBills>> = itemDao.getAllBills().asLiveData()

    var x:String?=null

    fun retrieveItem(id:Int):LiveData<Item>{
        return itemDao.getItem(id).asLiveData()
    }
    fun retrieveBill(id:Int):LiveData<Bill>{
        return itemDao.getBill(id).asLiveData()
    }
    fun currentBill(billno: Int): LiveData<List<Bill>> {
        return itemDao.getCurrBill(billno).asLiveData()
    }

    fun retrieveBillImage(id: Int):LiveData<AllBills>{
        return itemDao.getBillImage(id).asLiveData()
    }

    fun deleteItem(item: Item){
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
    //for bill
    fun deleteBillItem(bill: Bill){
        viewModelScope.launch {
            itemDao.deletebill(bill)
        }
    }
    private fun getUpdatedItemEntry(id:Int,name:String,price: String,quantity: String):Item{
        return Item(
            id=id,
            itemName = name,
            itemPrice = price.toDouble(),
            itemQuantity = quantity.toInt()
        )
    }
    fun updateItem(id:Int,name: String,price: String,quantity: String){
        val updatedItem=getUpdatedItemEntry(id,name,price,quantity)
        updateItem(updatedItem)
    }
    private fun updateItem(item: Item){
        viewModelScope.launch {
            itemDao.update(item)
        }
    }
    //for bill
    private fun getUpdatedBillItemEntry(billno:String,id: Int,name: String,price: String,quantity: String):Bill{
        return Bill(
            billNo = billno.toInt(),
            id = id,
            itemName = name,
            itemPrice = price.toDouble(),
            itemQuantity = quantity.toInt()
        )
    }
    fun updateBillItem(billno: String,id: Int,name:String,price: String,quantity: String){
        val updatedBillItem=getUpdatedBillItemEntry(billno,id, name, price, quantity)
        updateBillItem(updatedBillItem)
    }
    private fun updateBillItem(bill: Bill){
        viewModelScope.launch {
            itemDao.updatebill(bill)
        }
    }
    private fun insertItem(item:Item){
        viewModelScope.launch{
            itemDao.insert(item)
        }
    }
    private fun getNewItemEntry(item:String, price:String,quantity:String):Item{
        return Item(
            itemName = item,
            itemPrice = price.toDouble(),
            itemQuantity = quantity.toInt()
        )
    }
    fun addNewItem(item:String,price:String,quantity: String){
        val newItem=getNewItemEntry(item,price,quantity)
        insertItem(newItem)
    }
    //for bill
    private fun insertBillItem(bill: Bill){
        viewModelScope.launch {
            itemDao.insertbill(bill)
        }
    }
    fun getNewBillItemEntry(billno:String, item:String, price:String,quantity: String):Bill{
        return Bill(
            billNo=billno.toInt(),
            itemName = item,
            itemPrice = price.toDouble(),
            itemQuantity = quantity.toInt()
        )
    }

    fun addNewBillItem(billno:String,item:String,price:String,quantity:String){
        val newBillItem=getNewBillItemEntry(billno,item,price,quantity)
        insertBillItem(newBillItem)

    }

    fun isEntryValid(item:String,price:String,quantity:String):Boolean{
        if(item.isBlank()||price.isBlank()||quantity.isBlank()){
            return false
        }
        return true
    }

    //test
    private fun savebill(allBills: AllBills){
        viewModelScope.launch {
            itemDao.savebill(allBills)
        }
    }
    private fun getbillEntry(billno:Int,bill:Bitmap):AllBills{
        return AllBills(
            billno = billno,
            bill = bill
        )
    }
    fun getbill(billno: Int,bill:Bitmap){
        val newbill=getbillEntry(billno,bill)
        savebill(newbill)
    }


}
class InventoryViewModelFactory(private val itemDao: ItemDao):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("Unchecked_Cast")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}