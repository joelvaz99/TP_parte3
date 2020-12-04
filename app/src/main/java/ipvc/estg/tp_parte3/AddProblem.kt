package ipvc.estg.tp_parte3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddProblem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_problem)
        val ir_notas = findViewById<Button>(R.id.button_save)
        ir_notas.setOnClickListener {
            val intent = Intent(this@AddProblem, MapsActivity::class.java)
            startActivity(intent)
        }
    }
    1
}