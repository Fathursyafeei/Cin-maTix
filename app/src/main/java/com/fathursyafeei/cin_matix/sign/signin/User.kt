package com.fathursyafeei.cin_matix.sign.signin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var email:String ?= "",
    var nama:String ?= "",
    var password:String ?= "",
    var saldo:String ?= "",
    var url:String ?= "",
    var username:String ?= ""
) : Parcelable