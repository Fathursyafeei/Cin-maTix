package com.fathursyafeei.cin_matix.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// di set Parcelable artinya kalo di bawa ke page manapun itu lebih enak
@Parcelize
data class Checkout (
    // Menyesuaikan bentuk data
    var kursi : String ? = "", // Nullable (jadi kalo kosong di buat jd string)
    var harga : String ? = ""
) : Parcelable