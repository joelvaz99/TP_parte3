package ipvc.estg.tp_parte3

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.tp_parte3.adapter.NotaAdapter
import ipvc.estg.tp_parte3.entities.Nota
import ipvc.estg.tp_parte3.viewModel.NotaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Notas : AppCompatActivity(), NotaAdapter.RowClickListener  {
    private lateinit var notaViewModel: NotaViewModel
    private val newWordActivityRequestCode = 1
    private val newWordActivityRequestCode2 = 2

    lateinit var recyclerViewAdapter: NotaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = NotaAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)


                //ViewModel
                notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)
                notaViewModel.allNotas.observe(this, Observer { notas ->
                    // Update the cached copy of the notas in the adapter.
                    notas?.let { adapter.setNotas(it) }
                })

                //Fab
                val fab = findViewById<FloatingActionButton>(R.id.fab)
                fab.setOnClickListener {
                    val intent = Intent(this@Notas, AddNota::class.java)
                    startActivityForResult(intent, newWordActivityRequestCode)
                }



            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)

                if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
                    val ptitulo = data?.getStringExtra(AddNota.EXTRA_REPLY_TITULO)
                    val pdescricao = data?.getStringExtra(AddNota.EXTRA_REPLY_DESCRICAO)
                    val agora: LocalDateTime = LocalDateTime.now()
                    val formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    val dataFormatada = formatterData.format(agora)
                    if (ptitulo!= null && pdescricao != null) {
                        val nota = Nota(titular = ptitulo, nota = pdescricao,data = dataFormatada)
                        notaViewModel.insert(nota)
                    }

                }else if (requestCode == newWordActivityRequestCode2 && resultCode == Activity.RESULT_OK) {
                    val ptitulo = data?.getStringExtra(AddNota.EXTRA_REPLY_UPTITULO)
                    val pdescricao = data?.getStringExtra(AddNota.EXTRA_REPLY_UPDESCRICAO)

                    if (ptitulo!= null && pdescricao != null) {
                        notaViewModel.updateNota(ptitulo, pdescricao)
                    }
                }else {
                    Toast.makeText(
                        applicationContext,
                        "Titulo ou descricao vazia",
                        Toast.LENGTH_LONG).show()
                }

            }

            override fun onDeleteUserClickListener(item: Nota, position: Int) {
                notaViewModel.deleteNota(item.titular)
            }


            override fun onEditUserClickListener(item: Nota, position: Int) {
                val intent= Intent(this@Notas, AddNota::class.java)
                var titulo = item.titular.toString()
                var descricao = item.nota.toString()
                intent.putExtra("titulo",titulo)
                intent.putExtra("descricao",descricao)

                startActivityForResult(intent,newWordActivityRequestCode2)

    }
}