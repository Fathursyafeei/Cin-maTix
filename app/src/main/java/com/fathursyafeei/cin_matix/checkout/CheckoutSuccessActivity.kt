package com.fathursyafeei.cin_matix.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_home.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@CheckoutSuccessActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}