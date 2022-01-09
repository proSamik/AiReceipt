package com.example.aireceipt

import android.app.Application
import com.example.aireceipt.room.ItemRoomDatabase

class InventoryApplication:Application() {
    val database:ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this)}
}