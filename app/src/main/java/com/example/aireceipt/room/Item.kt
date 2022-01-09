package com.example.aireceipt.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val itemName:String,
    val itemPrice:Double,
    val itemQuantity:Int
)