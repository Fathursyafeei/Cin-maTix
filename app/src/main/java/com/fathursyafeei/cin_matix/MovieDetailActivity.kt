package com.fathursyafeei.cin_matix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fathursyafeei.cin_matix.checkout.PilihBangkuActivity
import com.fathursyafeei.cin_matix.home.dashboard.PlaysAdapter
import com.fathursyafeei.cin_matix.model.Film
import com.fathursyafeei.cin_matix.model.Plays
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private  lateinit var mDatabase : DatabaseReference
    private  var dataList = ArrayList<Plays>()
    private val linkDB :String = "https://cin-matix-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Ambil data dari yg sudah dikirim dr DashboardFragment
        val data = intent.getParcelableExtra<Film>("data")
        mDatabase = FirebaseDatabase.getInstance(linkDB).getReference("Film")
            .child(data!!.judul.toString())
            .child("play")


        tv_kursi.text = data!!.judul

        tv_genre.text = data.genre
        tv_desc.text = data.desc
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .placeholder(R.drawable.ic_imgerorr)
            .error(R.drawable.ic_imgerorr)
            .into(iv_poster)

        rv_who_play.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // ambil data
        getData()

        btn_pilih_bangku.setOnClickListener {
            var intent = Intent(this@MovieDetailActivity, PilihBangkuActivity::class.java)
                .putExtra("data",data)
            startActivity(intent)
        }

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // clear datalist agar tidak bentrok
                dataList.clear()

                for (getdataSnapshot in snapshot.children){
                    var Film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(Film!!)
                }

                rv_who_play.adapter = PlaysAdapter(dataList){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MovieDetailActivity,
                    ""+ error, Toast.LENGTH_LONG).show()
            }

        })
    }
}