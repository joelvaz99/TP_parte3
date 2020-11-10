package ipvc.estg.tp_parte3
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.tp_parte3.adapter.NotaAdapter
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.OutputPost
import ipvc.estg.tp_parte3.api.ServiceBuilder
import ipvc.estg.tp_parte3.entities.Nota
import ipvc.estg.tp_parte3.viewModel.NotaViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ir_notas = findViewById<Button>(R.id.notas)
        ir_notas.setOnClickListener {
            val intent = Intent(this@MainActivity, Notas::class.java)
            startActivity(intent)
        }
    }

    fun post(view: View) {
        val username1 = username.text.toString().trim()
        val password = password.text.toString().trim()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postTest(username1,password)

        call.enqueue(object : Callback<OutputPost> {
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                if (response.isSuccessful){
                    if(response.body()?.error == false){
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@MainActivity,"Username ou Palavra-passe errrado",  Toast.LENGTH_SHORT).show()
                    }else{
                        val intent = Intent(this@MainActivity, Mapa::class.java)
                        startActivity(intent)

                        //SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@MainActivity, "Login Efectuado", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
