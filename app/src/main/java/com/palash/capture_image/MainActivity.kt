package com.palash.capture_image


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var captureButton: Button
    private lateinit var galleryButton: Button
    lateinit var imageUri: Uri

    private val contact = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        imageView.setImageURI(null)
        imageView.setImageURI(imageUri)
    }

    //for gallery
    private val galleryContact = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(null)
        imageView.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        captureButton = findViewById(R.id.captureBtn)
        galleryButton = findViewById(R.id.galleryBtn)

        imageUri = createImageUri()!!

        //camera button click....................
        captureButton.setOnClickListener {
            contact.launch(imageUri)
            Log.d("Img", imageUri.path.toString()) //   /my_camera_photo_app/camera_photo.png
            Log.d("Img1", imageUri.toString()) //    content://com.palash.capture_image.fileProvider/my_camera_photo_app/camera_photo.png
        }

        //gallery button click...............
        galleryButton.setOnClickListener {

            galleryContact.launch("image/*")
        }

    }

    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            applicationContext,
            "com.palash.capture_image.fileProvider",
            image
        )
    }
}