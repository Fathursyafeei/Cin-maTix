package com.fathursyafeei.cin_matix.sign.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fathursyafeei.cin_matix.home.HomeActivity
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.sign.signup.SignUpActivity
import com.fathursyafeei.cin_matix.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    // declaration varible
    lateinit var iUsername:String
    lateinit var iPassword:String

    var linkDB = "https://cin-matix-default-rtdb.asia-southeast1.firebasedatabase.app/"

    // Inisiasi dari firebase
    lateinit var mDatabase : DatabaseReference

    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Isi Valuenya
        mDatabase = FirebaseDatabase.getInstance(linkDB).getReference("User")
        preference = Preferences(this)

        //set value untuk onboarding agar jika user sudah click next2
        // maka onboarding tdk ditampilkan lagi
        preference.setValues("onboarding", "1")
        // Cek Status
        if(preference.getValues("status").equals("1")){
            finishAffinity()

            var goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_masuk.setOnClickListener {
            // Value untuk menampung edit text
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            // Jika username kosong
            if(iUsername.equals("")){
                et_username.error = "Silahkan isi username Anda!"
                et_username.requestFocus()
            }
            // Jika password kosong
            else if(iPassword.equals("")){
                et_password.error = "Silahkan isi password Anda!"
                et_password.requestFocus()
            }
            // Jika username dan pass ada isinya
            else{
                pushLogin(iUsername, iPassword)
            }
        }

        btn_daftar.setOnClickListener {
            var goSignUp = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(goSignUp)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        /* Ambil Child username dimana dilakukan pengecekan terlebih
        dahulu user dengan inputan username nya sehingga bentuknya jd
        User kemudian "1" atau indexnya dimana "1" ini usernamenya
         */


        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            // Implement member
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                /* class ini adalah sebuah model yang menangani data User.
                Nah di db User memiliki detail (nama, emali, etc)
                akan ditampung di class ini
                 */
                var user = dataSnapshot.getValue(User::class.java)


                // Jika datanya kosong / null9
                if(user == null){
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan!",
                        Toast.LENGTH_LONG).show()
                }
                // Jika Datanya ada
                else{
                    // Pengecekan pass sama atau gk dgn input pass
                    if(user.password.equals(iPassword)){
                        // Jika sukses maka kita akan men set data2 di sini
                        // Simpan beberapa value
                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("user", user.username.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
                        preference.setValues("status", "1") // Status dari login ( 1 = login, 0 = belum login)

                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@SignInActivity, "Password Anda salah!",
                            Toast.LENGTH_LONG).show()
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message,
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}