package ipvc.estg.tp_parte3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Mapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
    }

    fun sair(view: View){
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        val intent = Intent(this@Mapa, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        var editor = token.edit()
        editor.putString("loginusername", " ")
        editor.commit()
        startActivity(intent)
    }
}