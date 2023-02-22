package com.fathursyafeei.cin_matix.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.home.tiket.TiketActivity
import com.fathursyafeei.cin_matix.model.Checkout
import com.fathursyafeei.cin_matix.model.Film
import com.fathursyafeei.cin_matix.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")

        tv_judul.text = data!!.judul

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt() // ambil value dataList[a].harga, !! artinya data pasti tidak kosong
        }

        dataList.add(Checkout("Total harus dibayar", total.toString()))

        rv_checkout.layoutManager = LinearLayoutManager(this) // versi vertical
        rv_checkout.adapter = CheckoutAdapter(dataList){

        }

        btn_tiket.setOnClickListener {
            var intent = Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java)
                .putExtra("data", data)
            startActivity(intent)

            showNotif(data!!)
        }

        iv_back.setOnClickListener {
            finish()
        }

    }

    private fun showNotif(datas: Film) {
        val NOTIFICATION_CHANNEL_ID = "channel_cinmatix_notif" // berfungsi agar bisa menghandle jika terdapat lebih dari satu notif
        val context = this.applicationContext
        var notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager // jika os android buka oreno ini aja boleh
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val channelName = "Cin-maTix Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel) // jika os android oreo atau diatasnya harus menambahkan code ini
        }

        // menyisipkan intent agar jika notif diklik pindah ke checkout aktivity
//        val mIntent = Intent(this, CheckoutActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString("id", "Id_film")
//        mIntent.putExtras(bundle)

        val mIntent = Intent(this, TiketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        mIntent.putExtras(bundle)

        // fungsinya untuk menampung mintent diatas
        val pendingIntent =
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_IMMUTABLE)

        // Aturan Notif
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.logo_notif)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.logo_notif
                )
            )
            .setTicker("notif cinmatix starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.GREEN,3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Yey Berhasil di beli 😘")
            .setContentText("Tiket " + datas.judul + "\tberhasil kamu dapatkan, Enjoy the Movie 🥰!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())


    }
}