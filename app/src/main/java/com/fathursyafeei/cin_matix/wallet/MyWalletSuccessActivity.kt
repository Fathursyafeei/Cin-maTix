package com.fathursyafeei.cin_matix.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.home.HomeActivity
import kotlinx.android.synthetic.main.activity_my_wallet_success.*

class MyWalletSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_success)


        btn_home.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@MyWalletSuccessActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        btn_wallet.setOnClickListener {

            var intentTiket = Intent(this@MyWalletSuccessActivity, MyWalletActivity::class.java)
            startActivity(intentTiket)
        }
    }
}