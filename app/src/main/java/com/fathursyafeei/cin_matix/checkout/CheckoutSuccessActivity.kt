package com.fathursyafeei.cin_matix.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.home.HomeActivity
import com.fathursyafeei.cin_matix.home.tiket.TiketActivity
import com.fathursyafeei.cin_matix.model.Film
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)
        val data = intent.getParcelableExtra<Film>("data")

        btn_home.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@CheckoutSuccessActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        btn_tiket.setOnClickListener {

            var intentTiket = Intent(this@CheckoutSuccessActivity, TiketActivity::class.java)
                .putExtra("data", data)
            startActivity(intentTiket)
        }
    }
}