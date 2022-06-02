package ds.project.tadaktadakfront

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class SetImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_image)

        val currentPhotoPath: String? = intent.getStringExtra("path")
        val imageView: ImageView=findViewById(R.id.set_iv)

        Glide.with(this@SetImageActivity)
            .load(currentPhotoPath)
            //.error(ColorDrawable(Color.RED))
            //.placeholder(R.drawable.splash)
            .into(imageView)
    }
}
