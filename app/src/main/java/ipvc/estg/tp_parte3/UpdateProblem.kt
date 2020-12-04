package ipvc.estg.tp_parte3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.OutputPost
import ipvc.estg.tp_parte3.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_add_nota.edit_word2
import kotlinx.android.synthetic.main.activity_add_problem.edit_word3
import kotlinx.android.synthetic.main.activity_update_problem.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProblem : AppCompatActivity() {
    private var id1: Any? = null
    private var loginusername: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_problem)
        var descricao = intent.getStringExtra("descricao")
        var username = intent.getStringExtra("username")
        loginusername=username
        var type = intent.getStringExtra("type")
        var id = intent.getStringExtra("id")
        id1=id
        edit_word2.setText(descricao)
        edit_word3.setText(type)




        button_save1.setOnClickListener{

            val descricao1 = edit_word2.text.toString().trim()
            val tipo1 = edit_word3.text.toString().trim()
           // tipo1.toInt()

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.updatePonto(id1,descricao1,tipo1)
            call.enqueue(object : Callback<OutputPost> {
                override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                    if (response.isSuccessful) {

                        if(response.body()?.error == false){
                            Toast.makeText(this@UpdateProblem,"Erro ao Atulizar",  Toast.LENGTH_SHORT).show()
                        }else{
                            val intent = Intent(this@UpdateProblem, MapsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("username", loginusername)
                            startActivity(intent)
                            Toast.makeText(this@UpdateProblem, "Registo Atulizado com sucesso", Toast.LENGTH_SHORT).show()
                        }


                    }


                }

                override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                    Toast.makeText(this@UpdateProblem, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }

        cancel_button1.setOnClickListener {
            val intent = Intent(this@UpdateProblem, MapsActivity::class.java)
            intent.putExtra("username",loginusername)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
        return true
    }

    // Logout
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {


            R.id.remover ->{

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.deletePontoid(id1)
                var position: LatLng
                call.enqueue(object : Callback<OutputPost> {
                    override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                        if (response.isSuccessful) {

                            if(response.body()?.error == false){
                                Toast.makeText(this@UpdateProblem,"Erro ao eliminar",  Toast.LENGTH_SHORT).show()
                            }else{
                                val intent = Intent(this@UpdateProblem, MapsActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("username", loginusername)
                                startActivity(intent)
                                Toast.makeText(this@UpdateProblem, "Registo apagado com sucesso", Toast.LENGTH_SHORT).show()
                            }




                            }


                    }

                    override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                        Toast.makeText(this@UpdateProblem, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })






                true
            }



            else -> super.onOptionsItemSelected(item)
        }
    }


}