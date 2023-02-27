package com.fathursyafeei.cin_matix.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.model.Wallet
import com.fathursyafeei.cin_matix.utils.Preferences
import com.fathursyafeei.cin_matix.wallet.adapter.WalletAdapter
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet.tv_saldo
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import com.fathursyafeei.cin_matix.utils.Currency

class MyWalletActivity : AppCompatActivity() {

    private var dataList = ArrayList<Wallet>() // ini diinisialisasi dikarenakan agar misal data blm ada maka app gk akan force close

    private lateinit var preferences: Preferences
    private lateinit var currecy : Currency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        preferences = Preferences(this)
        currecy = Currency()

        if (preferences.getValues("saldo") != ""){
            currecy.currency(preferences.getValues("saldo")!!, tv_saldo)
        }

        dataDummy()

    }

    private fun iniListener (){
        rv_transaksi.layoutManager = LinearLayoutManager(this)
        rv_transaksi.adapter = WalletAdapter(dataList){

        }

        btn_top_up.setOnClickListener {
            var intent = Intent(this, TopUpActivity::class.java)
            startActivity(intent)
        }

        iv_back.setOnClickListener {
            finish()
        }
    }


    private fun dataDummy (){

        dataList.add(
            Wallet(
                "Frozen",
                "Sabtu 03 Jul, 2023",
                70000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Top Up",
                "Sabtu 12 Jul, 2023",
                250000.0,
                "1"
            )
        )
        dataList.add(
            Wallet(
                "OnePiece",
                "Sabtu 22 Sep, 2023",
                70000.0,
                "0"
            )
        )

        iniListener()

    }
}