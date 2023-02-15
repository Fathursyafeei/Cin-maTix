package com.fathursyafeei.cin_matix.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>,
                      private val listener:(Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent,false )
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        //inisialisasi view
        private val tvTitle = view.findViewById<TextView>(R.id.tv_kursi)
        private val tvHarga = view.findViewById<TextView>(R.id.tv_harga)

        fun bindItem(data:Checkout, listener: (Checkout) -> Unit, context: Context){

            val localID = Locale("id", "ID") // Karena indo tdk terdaftar jd dibuat default seperti ini
            val formatRp = NumberFormat.getCurrencyInstance(localID)
            tvHarga.setText(formatRp.format(data.harga!!.toDouble()))


            // karna menggunakan list maka status nya begini:
            if(data.kursi!!.startsWith("Total")){
                // tampilkan data kursi
                tvTitle.setText(data.kursi)
                // pada tv title di edit komponen drwable nya(drawableLeft) atau bakal dihilangi
                tvTitle.setCompoundDrawables(null,null, null, null)
            }
            // kalau datanya tidak dimulai dari Total
            else{
                tvTitle.setText("Seat No. " + data.kursi)
            }



            itemView.setOnClickListener {
                listener(data)
            }
        }

    }


}
