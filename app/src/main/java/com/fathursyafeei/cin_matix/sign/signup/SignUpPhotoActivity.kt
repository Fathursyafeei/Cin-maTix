package com.fathursyafeei.cin_matix.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fathursyafeei.cin_matix.home.HomeActivity
import com.fathursyafeei.cin_matix.R
import com.fathursyafeei.cin_matix.sign.signin.User
import com.fathursyafeei.cin_matix.utils.Preferences
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import java.util.*

class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1 // Inisialisasi variable untuk pencarian foto
    // Variable untuk status pencarian foto
    // ( jika sudah menambahkan sebuah foto maka bernilai true )
    var statusAdd:Boolean = false
    lateinit var filePath: Uri // variable untuk path file jika mencari sebuah data di photo

    lateinit var storage: FirebaseStorage  // inisialisasi variable firebase storage
    lateinit var storageReferensi : StorageReference
    lateinit var preferences : Preferences

    // Inisialisai Realtime DB Firebase
    lateinit var user : User
    lateinit var linkDB: String
    private lateinit var mFirebaseDB : DatabaseReference
    private lateinit var mFirebaseInstance : FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)

        // Inisialisasi variable yang belum ada valuenya
        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        // Inisialisasi value Realtime DB
        linkDB = "https://cin-matix-default-rtdb.asia-southeast1.firebasedatabase.app/"
        mFirebaseInstance = FirebaseDatabase.getInstance(linkDB)
        mFirebaseDB = mFirebaseInstance.getReference("User")

        // bikin value untuk textview hello
        user = intent.getParcelableExtra("data")!!
        tv_helllo.text = "Selamat datang\n" + user.nama + "👋"

        //bagian add data ( ini untuk membuka browser photo)
        iv_add.setOnClickListener {
            if(statusAdd){
                statusAdd = false // misalkan status addnya true maka akan di ganti ke false
                btn_save.visibility = View.VISIBLE // & button save di munculkan
                iv_add.setImageResource(R.drawable.ic_btn_upload) // yg td awalnya icon add -> delete
                iv_profile.setImageResource(R.drawable.user_pic) // ini default profile
            }
            // Kalau statusAddnya false
            else{
//                Dexter.withActivity(this)
//                    .withPermission(Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()

                ImagePicker.with(this)
                    .cameraOnly()	//User can only capture image using Camera
                    .compress(1024)
                    .start()

            }
        }

        btn_home.setOnClickListener {
            finishAffinity()
            var goHome = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        // Untuk upload ke firebase
        btn_save.setOnClickListener {
            //Kondisi kalau path nya tidak null
            if(filePath != null){
                // Bagian upload photo
                //menampilkan progress dialog agar bisa menampilkan informasi kepada
            // user bahwa sedang melakukan upload
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading... 😫")
                progressDialog.show() // ditampilkan

                /* Bagian Firebase */
                var ref = storageReferensi.child("images/" + UUID.randomUUID().toString()) // UUId agar tidak bentrok filenya
                ref.putFile(filePath) // kasih filenya menggunakan file Uri (filePath)
                    .addOnSuccessListener {
                        // Kalau Success
                        progressDialog.dismiss() // Matikan Progress dialognya
                        Toast.makeText(this@SignUpPhotoActivity,
                            "Uploaded 🥳",Toast.LENGTH_LONG).show()

                        // Url nya di save ke sharedReference
                        ref.downloadUrl.addOnSuccessListener {
//                            preferences.setValues("url", it.toString())
                            // Save to firebase
                            saveToFirebase(it.toString())

                        }

                        // matikan activity yang ada
//                        finishAffinity()
//                        var goHome = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
//                        startActivity(goHome)

                    }
                        // Kalaau tidak success
                    .addOnFailureListener{
                        progressDialog.dismiss() // matikan progress dialog
                        Toast.makeText(this@SignUpPhotoActivity,
                            "Failed 🥺",Toast.LENGTH_LONG).show()
                    }

                    // Menampilan progress sudah berapa persen di upload
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload 🥱 " + progress.toInt() + " %")
                    }


            }
            // Kondisi kalo pathnya null
            else{
                Toast.makeText(this@SignUpPhotoActivity,
                    "Anda belum menekan pencarian foto 😩",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveToFirebase(url: String) {
        mFirebaseDB.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user.url = url

                mFirebaseDB.child(user.username!!).setValue(user)

                preferences.setValues("nama", user.nama.toString())
                preferences.setValues("user", user.username.toString())
                preferences.setValues("saldo", "")
                preferences.setValues("url", "")
                preferences.setValues("email", user.email.toString())
                preferences.setValues("status", "1")
                preferences.setValues("url", url)

                finishAffinity()
                val goHome = Intent(this@SignUpPhotoActivity,
                    HomeActivity::class.java)
                startActivity(goHome)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpPhotoActivity, "" + error.message,
                    Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        // Kalau di setujuin
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
//            takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }

        ImagePicker.with(this)
            .cameraOnly()
            .compress(1024)
            .start()  //User can only capture image using Camera
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this@SignUpPhotoActivity,
            "Anda tidak bisa menambahkan photo profile 😭",Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: com.karumi.dexter.listener.PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        Toast.makeText(this@SignUpPhotoActivity,
            "Tergesah? klik tombol upload nanti aja 😟",Toast.LENGTH_LONG).show()
    }

    // Karna disini menggunakan startActForResult maka func ini pasti ada :
//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        // ini untuk pencarian photo
//        if(requestCode == REQUEST_IMAGE_CAPTURE && requestCode == Activity.RESULT_OK){
//            // file bitmap nya sudah dapat maka
//            var bitmap = data?.extras?.get("data") as Bitmap
//            // maka dinisi status add itu true
//            statusAdd = true
//
//            // file path diambil :
//            filePath = data.getData()!!
//
//            //Munculkan photonya di profile :
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform()) // mengubah bentuk photo menjadi lingkaran
//                .into(iv_profile)
//
//            btn_save.visibility = View.VISIBLE // button save dimunculkan
//            iv_add.setImageResource(R.drawable.ic_btn_delete) // ubah btn add(upload) jadi delete
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            // Image Uri akan tidak null untuk RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)


            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_btn_delete)
        }
        else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this@SignUpPhotoActivity, ImagePicker.getError(data),
                Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this@SignUpPhotoActivity, "Anda gagal mengambil photo 😓",
                Toast.LENGTH_LONG).show()
        }
    }

}