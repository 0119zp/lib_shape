package zpan.example.shape

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_to_shape_button.setOnClickListener {
            val intent = Intent(this, ShapeButtonActivity::class.java)
            startActivity(intent)
        }

        btn_to_shape_layout.setOnClickListener {
            val intent = Intent(this, ShapeLayoutActivity::class.java)
            startActivity(intent)
        }
    }
}
