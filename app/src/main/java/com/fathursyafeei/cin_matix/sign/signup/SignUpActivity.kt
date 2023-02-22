package com.fathursyafeei.cin_matix.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.sign.signin.SignInActivity
import com.fathursyafeei.cin_matix.sign.signin.User
import com.fathursyafeei.cin_matix.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sPassword:String
    lateinit var sNama:String
    lateinit var sEmail:String

    // Inisialisasi Firebase
    lateinit var mFirebaseInstance : FirebaseDatabase
    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var linkDB:String

    private lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Inisialisasi value
        linkDB = "https://cin-matix-default-rtdb.asia-southeast1.firebasedatabase.app/"
        mFirebaseInstance = FirebaseDatabase.getInstance(linkDB)
        mDatabase = FirebaseDatabase.getInstance(linkDB).getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        im_back.setOnClickListener {
            var goBack = Intent (this@SignUpActivity, SignInActivity::class.java)
            startActivity(goBack)
        }

        btn_lanjutkan.setOnClickListener {

            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()

            // Validasi Data
            if(sUsername.equals("")){
                et_username.error = "Silahkan isi username Anda!"
                et_username.requestFocus()
            }
            else if(sPassword.equals("")){
                et_password.error = "Silahkan isi password Anda!"
                et_password.requestFocus()
            }
            else if(sNama.equals("")){
                et_nama.error = "Silahkan isi nama Anda!"
                et_nama.requestFocus()
            }
            else if(sEmail.equals("")){
                et_email.error = "Silahkan isi email Anda!"
                et_email.requestFocus()
            }
            else{
                //Fix Bug with fill have "."
                var statusUsername = sUsername.indexOf(".")
                if (statusUsername >= 0){
                    et_username.error = "Silahkan isi Username Anda tanpa ."
                    et_username.requestFocus()
                }
                else{
                    // Func penyimpanan data ke firebase
                    saveUser(sUsername, sPassword, sNama, sEmail)
                }

            }

        }

    }

    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        // Menampung data agar func tidak terlalu banyak
        var user = User()  //Mengambil / meng extend dari  class User
        //set Value
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama
        user.email = sEmail

        if(sUsername != null){
            // Pengecekan apakah usernamenya terdaftar atau gk
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+ databaseError.message,
                    Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                // Pengecekan data
                // jika null akunya bisa dibuat
                if(user == null || user.toString() == ""){

                    // Set Database (Menyimpan ke database)
                    mDatabaseReference.child(sUsername).setValue(data)

                    preferences.setValues("nama", data.nama.toString())
                    preferences.setValues("user", data.username.toString())
                    preferences.setValues("saldo", "")
                    preferences.setValues("url", "")
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("status", "1")

                    // Setelah selesai melakukan penyimpanan maka lempar ke signup photo adtivity
                    var goToSignUpPhoto = Intent(this@SignUpActivity,
                        SignUpPhotoActivity::class.java).putExtra("data", data)
                    startActivity(goToSignUpPhoto)

                }
                else{
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan!",
                        Toast.LENGTH_SHORT).show()
                }

            }

        })
    }
}