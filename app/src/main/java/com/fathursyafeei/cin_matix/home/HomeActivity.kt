package com.fathursyafeei.cin_matix.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.home.dashboard.DashboardFragment
import com.fathursyafeei.cin_matix.home.setting.SettingFragment
import com.fathursyafeei.cin_matix.home.tiket.TicketFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentDashboard = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentSetting = SettingFragment()


        setFragment(fragmentDashboard)


        iv_menu1.setOnClickListener {
            setFragment(fragmentDashboard)

            changeIcon(iv_menu1, R.drawable.ic_home_active)
            changeIcon(iv_menu2, R.drawable.ic_ticket)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }

        iv_menu2.setOnClickListener {
            setFragment(fragmentTicket)

            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_ticket_active)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }

        iv_menu3.setOnClickListener {
            setFragment(fragmentSetting)

            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_ticket)
            changeIcon(iv_menu3, R.drawable.ic_profile_active)
        }

    }

    // Func untuk melakukan load fragment
    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // load
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    // func untuk mengubah icon Menu
    private fun changeIcon(imageView: ImageView, int:Int){
        imageView.setImageResource(int)
    }
}