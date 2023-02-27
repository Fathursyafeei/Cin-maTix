package com.fathursyafeei.cin_matix.wallet.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.model.Wallet
import java.text.NumberFormat
import java.util.Locale

class WalletAdapter(private var data: List<Wallet>,
                    private val listener:(Wallet) -> Unit)
    : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WalletAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_transaksi, parent,false )
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        //inisialisasi view
        private val tvTitle = view.findViewById<TextView>(R.id.tv_movie)
        private val tvDate = view.findViewById<TextView>(R.id.tv_date)
        private val tvMoney = view.findViewById<TextView>(R.id.tv_money)

        fun bindItem(data:Wallet, listener: (Wallet) -> Unit, context: Context){

            tvTitle.text  = data.title
            tvDate.text  = data.date

            val localID = Locale ("in", "ID")
            val formatRp = NumberFormat.getCurrencyInstance(localID)

            if(data.status.equals("0")){
                tvMoney.text  = "- " + formatRp.format(data.money)
            }
            else{
                tvMoney.text  = "+ " + formatRp.format(data.money)
                tvMoney.setTextColor(Color.rgb(5, 186,189))
            }




            itemView.setOnClickListener {
                listener(data)
            }
        }

    }


}
