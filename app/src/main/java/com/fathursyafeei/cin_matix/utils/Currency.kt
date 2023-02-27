package com.fathursyafeei.cin_matix.utils

import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class Currency {
    public fun currency(harga: String?, textView: TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.text = format.format(harga!!.toDouble())
    }
}