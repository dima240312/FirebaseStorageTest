package com.example.firebasestoragetest

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.example.firebasestoragetest.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File


//Константы
val FILES = "files"
val IMAGES = "img"
val RES_PNG = ".png"
val RES_JPG = ".jpg"
val LOAD_MESSAGE = "Ищем вашу рыбу!.."


class MainActivity : AppCompatActivity() {

    var database_ref = FirebaseStorage.getInstance().reference
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.getImage.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage(LOAD_MESSAGE)
            progressDialog.setCancelable(false)
            progressDialog.show()


            val resName = binding.request.text.toString()
            val storageRef = database_ref.child(FILES).child("$IMAGES/$resName$RES_PNG")

            val localFile = File.createTempFile("tempImage", "png")

            storageRef.getFile(localFile)
                .addOnSuccessListener {

                    if(progressDialog.isShowing){progressDialog.dismiss()}


                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    binding.loadedImage.setImageBitmap(bitmap)


                }
                .addOnFailureListener{
                    if(progressDialog.isShowing){progressDialog.dismiss()}
                    Toast.makeText(this,"Наша рыба утонула :(",Toast.LENGTH_SHORT).show()

                }


        }

    }
}