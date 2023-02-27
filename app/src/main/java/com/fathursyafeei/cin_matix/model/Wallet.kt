package com.fathursyafeei.cin_matix.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// di set Parcelable artinya kalo di bawa ke page manapun itu lebih enak
@Parcelize
data class Wallet (
    // Menyesuaikan bentuk data
    var title : String ? = "", // Nullable (jadi kalo kosong di buat jd string)
    var date : String ? = "",
    var money : Double,
    var status : String ? = "" // untuk membedakan mana yang input dan mana yang output
) : Parcelable