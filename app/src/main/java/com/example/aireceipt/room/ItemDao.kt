package com.example.aireceipt.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("Select * from Item Order by id ASC")
    fun getItems(): Flow<List<Item>>

    @Query("Select * from Item Order by id ASC")
    fun getitems(): Flow<List<Item>>

    @Query("Select * from Item WHERE id=:id")
    fun getItem(id:Int):Flow<Item>

    @Insert
    suspend fun insertbill(bill: Bill)

    @Update
    suspend fun updatebill(bill: Bill)

    @Delete
    suspend fun deletebill(bill: Bill)

    @Query("Select * from Bill WHERE billNo=:billno Order by id ASC")
    fun getBills(billno:Int): Flow<List<Bill>>

    @Query("Select * from Bill WHERE id=:id")
    fun getBill(id:Int):Flow<Bill>

    @Query("Select * from Bill Where billNo = :billno Order by id ASC")
    fun getCurrBill(billno:Int):Flow<List<Bill>>

    @Insert
    suspend fun savebill(allBills: AllBills)

    @Query("Select * from AllBills Order by id ASC")
    fun getAllBills():Flow<List<AllBills>>

    @Query("Select * from AllBills Where id=:id")
    fun getBillImage(id:Int):Flow<AllBills>
}