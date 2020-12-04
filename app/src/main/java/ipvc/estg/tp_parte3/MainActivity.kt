package ipvc.estg.tp_parte3
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.OutputPost
import ipvc.estg.tp_parte3.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
//
    private var username2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ir_notas = findViewById<Button>(R.id.notas)
        ir_notas.setOnClickListener {
            val intent = Intent(this@MainActivity, Notas::class.java)
            startActivity(intent)
        }
    }
    //Login
    fun post(view: View) {
         username2 = username.text.toString().trim()
        val password = password.text.toString().trim()
        val passwordEncriptado= encriptar(password)
        //Toast.makeText(this@MainActivity,,  Toast.LENGTH_SHORT).show()
        Log.d("**** Joel", "new location received - " + passwordEncriptado)
        //login Maria -> 1234



        //Inserir
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postTest(username2,passwordEncriptado)

        call.enqueue(object : Callback<OutputPost> {
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                if (response.isSuccessful){
                    if(response.body()?.error == false){
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@MainActivity,"Username ou Palavra-passe errrado",  Toast.LENGTH_SHORT).show()
                    }else{
                        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
                        val intent = Intent(this@MainActivity, MapsActivity::class.java)
                        intent.putExtra("username",username2)
                       var editor = token.edit()
                        editor.putString("loginusername",username2)
                        editor.commit()
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)


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

    override fun onStart() {
        super.onStart()
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        if (token.getString("loginusername", " ") != " ") {
          val intent = Intent(applicationContext, MapsActivity::class.java)
            var user =token.getString("loginusername",username2)
            intent.putExtra("username",user)

               intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
               startActivity(intent)
          }


    }

    @Throws(NoSuchAlgorithmException::class)
    fun encriptar(senha: String): String? {
        return if (senha.length == 0) {
            null
        } else {
            val md: MessageDigest = MessageDigest.getInstance("SHA1")
            md.update(senha.toByteArray())
            val hash: ByteArray = md.digest()
            val senhaEncrip = StringBuffer()
            for (i in hash.indices) {
                senhaEncrip.append(Integer.toHexString((hash[i] and 0xff.toByte()).toInt()))
            }
            senhaEncrip.toString()
        }
    }

}
