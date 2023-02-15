package com.fathursyafeei.cin_matix.home.tiket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.model.Checkout
import com.fathursyafeei.cin_matix.model.Film
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.main_content.*

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        var data = intent.getParcelableExtra<Film>("data")

        tv_title.text = data!!.judul
        tv_genre.text = data.genre
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        rv_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("A1", ""))
        dataList.add(Checkout("A2", ""))

        rv_checkout.adapter = TiketAdapter(dataList){

        }

        iv_barcode_bar.setOnClickListener ( View.OnClickListener {
            showDialog()
        })

        iv_back.setOnClickListener {
            finish()

        }


    }

    private fun showDialog() {
        val view = layoutInflater.inflate(R.layout.dialog,null, )
        val dialog = BottomSheetDialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.setCancelable(false)

        val btnClose = dialog.findViewById<Button>(R.id.btn_close)
        btnClose!!.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}