package com.ecemsevvalcinar.w1_layout.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ecemsevvalcinar.w1_layout.databinding.ActivityUploadBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedImage: Uri? = null

    private lateinit var firestore : FirebaseFirestore
    private lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        registerLauncher()

        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
    }


    fun uploadClicked(view: View){

        val uuid = UUID.randomUUID() // universal unic id, random uuid vericek
        val imageName = "$uuid.jpg"


        val reference = storage.reference
        //images adli dosyanin icine kaydedilir images/imagename yazmakla aynı sey
        val imageReference = reference.child("images").child(imageName)

        if(selectedImage !=null){

            imageReference.putFile(selectedImage!!).addOnSuccessListener {
                // download url --> firestore
                Toast.makeText(this,"Posted succesfully",Toast.LENGTH_SHORT).show()
                val uploadPictureReference = storage.reference.child("images").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()

                    // key, value ikilisi seklinde depolanirlar, bu yuzden hashmap olusturup icine atiyoruz
                    val postMap = hashMapOf<String,Any>()
                    postMap.put("downloadUrl",downloadUrl)
                    postMap.put("about",binding.editTextAbout.text.toString())
                    postMap.put("bookName",binding.editTextBookName.text.toString())
                    postMap.put("date", Timestamp.now())

                    firestore.collection("Books").add(postMap).addOnSuccessListener {
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@UploadActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
                    }
                }


            }.addOnFailureListener{
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }

    fun selectImageClicked(view: View){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            // izin yok, izin iste
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //true donerse mantigini gostermek gerekiyor
                Snackbar.make(view,"Permission Needed for Gallery!", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }else{
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{ // zaten izin varsa
            val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //start activity for result
            activityResultLauncher.launch(intentToGallery)
        }
    }


    private fun registerLauncher(){
        //callback --> secildikten sonra ne olacak
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if(result.resultCode == RESULT_OK){
                    val intentFromResult = result.data // bize nullable intent donecek
                    if(intentFromResult !=null){
                        selectedImage = intentFromResult.data
                        selectedImage?.let {
                            binding.imageView.setImageURI(it) //bitmape cevirmeden bu sekilde yapilabilir
                            //cunku direkt firebase bunu uri olarak alip upload edebiliyor
                        }
                    }
                }
            })

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){ // izin verildi
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
            else{ // izin verilmedi
                Toast.makeText(this@UploadActivity,"Permission Needed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}