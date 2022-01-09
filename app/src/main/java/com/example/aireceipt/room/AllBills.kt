package com.example.aireceipt.room

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AllBills(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val billno:Int,
    val bill:Bitmap
)
