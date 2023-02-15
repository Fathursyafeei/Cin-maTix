// Database Sementara
// Dimana berfungsi agar user diset untuk tdk perlu lagi sign in berkali- kali
// serta menampilkan onboarding cukup untuk sekali saja

package com.fathursyafeei.cin_matix.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context:Context ) {
    // membuat object
    companion object{
        // Variabel konstan untuk aplikasinya
        const val USER_PREFF = "USER_PREFF"
    }

    //  var untuk menyimpan fun untuk sharePrefeerence
    var sharedPreferences = context.getSharedPreferences(USER_PREFF, 0)

    // var untuk men set  dan mengambil valuenya
    fun setValues(key : String, value : String ){
        // ini diibaratkan seperti perizinan
        // dimana kita melakukan izin apakah bisa menambahkan atau mengedit data didalam sini
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value) // Masukkan value
        editor.apply() // value di apply
    }

    fun getValues(key: String) : String?{
        return sharedPreferences.getString(key, "")
    }

}