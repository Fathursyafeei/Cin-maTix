package com.fathursyafeei.cin_matix.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.utils.Currency
import com.fathursyafeei.cin_matix.utils.Preferences
import kotlinx.android.synthetic.main.activity_my_wallet.tv_saldo
import kotlinx.android.synthetic.main.activity_top_up.*
import kotlinx.android.synthetic.main.row_item_transaksi.*
import java.util.*

class TopUpActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        preferences = Preferences(this)
        if (preferences.getValues("saldo") != ""){
            var formatUang = Currency()
            formatUang.currency(preferences.getValues("saldo"), tv_saldo)
        }

        iniListener()


//        tv_20k.setOnClickListener {
//            if(status20k){
//                deselectedMoney(tv_20k)
//                et_amount.setText("")
//            }
//            else{
//                selectedMoney(tv_20k)
//                et_amount.setText("19000")
//            }
//        }
//

    }

    private  fun iniListener(){

        tv_20k.setOnClickListener {
            pilihUang("19000",tv_20k)
        }
        tv_50k.setOnClickListener {
            pilihUang("49000",tv_50k)
        }
        tv_100k.setOnClickListener {
            pilihUang("99000",tv_100k)
        }
        tv_200k.setOnClickListener {
            pilihUang("199000",tv_200k)
        }
        tv_300k.setOnClickListener {
            pilihUang("299000",tv_300k)
        }
        tv_500k.setOnClickListener {
            pilihUang("499000",tv_500k)
        }


        btn_top_up.setOnClickListener {
            var intent = Intent(this, MyWalletSuccessActivity::class.java)
            startActivity(intent)

        }

        iv_back.setOnClickListener {
            finish()
        }

        etChange()
    }

    private fun etChange(){

        et_amount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    if(s.toString().toInt() >= 19000){
                        btn_top_up.visibility = View.VISIBLE
                    }
                    else{
                        deselectedMoney(tv_20k)
                        deselectedMoney(tv_50k)
                        deselectedMoney(tv_100k)
                        deselectedMoney(tv_200k)
                        deselectedMoney(tv_300k)
                        deselectedMoney(tv_500k)
                    }
                }
                catch (e : NumberFormatException){
                    deselectedMoney(tv_20k)
                    deselectedMoney(tv_50k)
                    deselectedMoney(tv_100k)
                    deselectedMoney(tv_200k)
                    deselectedMoney(tv_300k)
                    deselectedMoney(tv_500k)
                }
            }

        })
    }



    private fun pilihUang(uang : String, selectTv: TextView) {

        var tombols = listOf<TextView>(
            findViewById(R.id.tv_20k),
            findViewById(R.id.tv_50k),
            findViewById(R.id.tv_100k),
            findViewById(R.id.tv_200k),
            findViewById(R.id.tv_300k),
            findViewById(R.id.tv_500k)
        )

        tombols.forEach { tombol ->
            if( tombol == selectTv){
                selectedMoney(tombol)
            }
            else{
                deselectedMoney(tombol)
            }
        }

        et_amount.setText(uang) // harus di bawah ???
    }


    private fun selectedMoney(textView : TextView){
        btn_top_up.visibility = View.VISIBLE
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundResource(R.drawable.shape_ic_rectangle_turquoise)
    }
    private fun deselectedMoney(textView : TextView){
        textView.setTextColor(resources.getColor(R.color.blue_grey))
        textView.setBackgroundResource(R.drawable.shape_rectangle_blue)
        btn_top_up.visibility = View.INVISIBLE
    }



}