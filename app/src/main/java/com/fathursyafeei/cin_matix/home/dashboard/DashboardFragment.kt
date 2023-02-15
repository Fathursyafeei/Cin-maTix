package com.fathursyafeei.cin_matix.home.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.fathursyafeei.cin_matix.MovieDetailActivity
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.model.Film
import com.fathursyafeei.cin_matix.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase : DatabaseReference
    private val linkDB :String = "https://cin-matix-default-rtdb.asia-southeast1.firebasedatabase.app/"

    private var dataList = ArrayList<Film>()

//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance(linkDB).getReference("Film")

        tv_nama.text = preferences.getValues("nama")
        if(preferences.getValues("saldo") != ""){
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo) // is still fix it? -> (8.45)
        }

        // Tampilkan Gambar Profile
        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        // Siapin recycle View
        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) // horiZontal
        rv_coming_soon.layoutManager = LinearLayoutManager(context) // Menggunakan layour vertikal

        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // data list di clear dulu agar tidak ada data duplikat
                dataList.clear()

                // memanggil data
                for(getdataSnapshot in dataSnapshot.children){
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                // Set Adapter
                rv_now_playing.adapter = NowPlayingAdapter(dataList){
                    // Pindah activity dengan kirim data
                    var intent = Intent(context, MovieDetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)

                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context, MovieDetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, // gk this dan pake context dikarenakan in merupakan Fragment
                    ""+databaseError.message, Toast.LENGTH_LONG ).show()
            }

        })
    }

    // Func untuk melakukan konversi format duit
    private fun currency(harga : Double, textView : TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.text = format.format(harga)
    }



//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment DashboardFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic fun newInstance(param1: String, param2: String) =
//                DashboardFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}