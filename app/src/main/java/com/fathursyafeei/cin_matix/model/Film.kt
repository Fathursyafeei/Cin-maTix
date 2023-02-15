package com.fathursyafeei.cin_matix.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// di set Parcelable artinya kalo di bawa ke page manapun itu lebih enak
@Parcelize
data class Film (
    // Menyesuaikan bentuk data
    var desc : String ? = "", // Nullable (jadi kalo kosong di buat jd string)
    var director : String ? = "",
    var genre : String ? = "",
    var judul : String ? = "",
    var poster : String ? = "",
    var rating : String ? = ""
) : Parcelable