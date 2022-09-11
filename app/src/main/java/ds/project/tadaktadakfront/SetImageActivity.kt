package ds.project.tadaktadakfront

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import java.io.File


class SetImageActivity : AppCompatActivity() {


    private lateinit var textRecognizer: TextRecognizer
    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_image)

        textRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

        val currentPhotoPath: String? = intent.getStringExtra("path")
        val uriSelected = Uri.parse(intent.getStringExtra("path"))
        val uri: Uri = File(currentPhotoPath).toUri()
        Log.v("tag", "successI")

        val imageView: ImageView = findViewById(R.id.set_iv)
        Glide.with(this@SetImageActivity)
            .load(currentPhotoPath)
            .error(ColorDrawable(Color.RED))
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageView)


        // 2초 후 다음 액티비티로 넘김
        Handler(Looper.getMainLooper()).postDelayed({
            convertImagetoText(uriSelected)
            convertImagetoText(uri)
            Log.v("tag", "successT")
        }, 2000)


        //textView 선언
        textView = findViewById(R.id.image_text)

        //스크롤이 가능하게 함
        textView.setMovementMethod(ScrollingMovementMethod())


        // persistent bottom sheet로 사용할 view 획득해옴
//        bottom_sheet.xml 안에 설정되어 있으므로 그곳에서 findViewById로 얻어옴
        val bottomSheet = bottom_sheet.findViewById<View>(R.id.bottom_sheet)

        // 위에서 획득한 view를 Bottomsheet로 지정함
        lateinit var persistentBottomSheetBehavior: BottomSheetBehavior<*>
        persistentBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)


        persistentBottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            // bottom sheet가 스크롤 될때 호출됨

            override fun onSlide(p0: View, p1: Float) {
                //    var fulltext = textView.text.toString()
                //  employer_name.setText(fulltext)

            }

            //bottom sheet의 상태값이 변경될 때 호출됨
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                }
            }

        })


    } // end onCreate


    private fun convertImagetoText(imageUri: Uri?) {
        try {

            var inputImg = InputImage.fromFilePath(applicationContext, imageUri!!)
            Log.v("tag", "successC")
            val result: Task<Text> = textRecognizer.process(inputImg)
                .addOnSuccessListener {
                    Log.v("tag", "success")

                    textView.text = it.text

                    var employer_edit: TextInputEditText = findViewById(R.id.employer_edittext)
                    var wage_edit: TextInputEditText = findViewById(R.id.employee_wage_edittext)
                    var employee_edit: TextInputEditText = findViewById(R.id.employee_edittext)


//                    var fulltext = textView.text.toString()

                    // fulltext의 타입은 charSequence
                    var fulltext = textView.text
                    var test = fulltext.toString()


//                    var test = fulltext.get()
//                    employer_edit.text= Editable.Factory.getInstance().newEditable(fulltext) // 결과 어케 나올지 아직 안해봄 0617
                         employer_edit.setText(fulltext) // 모든 텍스트 들어감
                    //     employer_edit.setText(fulltext.subSequence(0,3)) //문정인
                    //    employee_edit.setText(fulltext.subSequence(24..26)) // 계속 랜덤하게 뜸..^
                    wage_edit.setText(fulltext.toString())
//                    wage_edit.setText(Int.valueOf(test1))


                }.addOnFailureListener {
                    textView.text = "Error : ${it.message}"

                }
        } catch (e: Exception) {

        }
    }

}
