﻿package ds.project.tadaktadakfront

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import kotlinx.android.synthetic.main.fragment_navi_setting.*


class SetImageActivity : AppCompatActivity() {


    private lateinit var textRecognizer: TextRecognizer
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_image)

        textRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

        val currentPhotoPath: String? = intent.getStringExtra("path")
        val uri = Uri.parse(intent.getStringExtra("path"))
        Log.v("tag", "successI")

        val imageView: ImageView=findViewById(R.id.set_iv)
        Glide.with(this@SetImageActivity)
            .load(currentPhotoPath)
            .error(ColorDrawable(Color.RED))
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageView)

        textView = findViewById(R.id.image_text)

        // 2초 후 다음 액티비티로 넘김
        Handler(Looper.getMainLooper()).postDelayed({
            convertImagetoText(uri)
            Log.v("tag", "successT")



        }, 2000)


    } // end onCreate

    private fun convertImagetoText(imageUri: Uri?) {
        try {

            var inputImg = InputImage.fromFilePath(applicationContext, imageUri!!)
            Log.v("tag", "successC")
            val result: Task<Text> = textRecognizer.process(inputImg)
                .addOnSuccessListener {
                    Log.v("tag", "success")
                    textView.text = it.text

                }.addOnFailureListener {
                    textView.text = "Error : ${it.message}"

                }
        } catch (e: Exception) {

        }
    }

}
