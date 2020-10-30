package ipvc.estg.tp_parte3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.tp_parte3.adapter.NotaAdapter
import ipvc.estg.tp_parte3.entities.Nota
import ipvc.estg.tp_parte3.viewModel.NotaViewModel

class MainActivity : AppCompatActivity(),NotaAdapter.RowClickListener  {
    private lateinit var notaViewModel: NotaViewModel
    private val newWordActivityRequestCode = 1

    lateinit var recyclerViewAdapter: NotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler view
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
            val intent = Intent(this@MainActivity, AddNota::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val ptitulo = data?.getStringExtra(AddNota.EXTRA_REPLY_TITULO)
            val pdescricao = data?.getStringExtra(AddNota.EXTRA_REPLY_DESCRICAO)

            if (ptitulo!= null && pdescricao != null) {
                val nota = Nota(titular = ptitulo, nota = pdescricao)
                notaViewModel.insert(nota)
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Titulo ou descricao vazia",
                Toast.LENGTH_LONG).show()
        }
    }



    override fun onDeleteUserClickListener(item: Nota, position: Int) {
     notaViewModel.deleteNota(item.titular)
    }


    override fun onItemClickListener(item: Nota, position: Int) {

       Toast.makeText(this, item.titular , Toast.LENGTH_SHORT).show()
        /*
       val intent = Intent(this, UpdateNota::class.java)
        intent.putExtra("TITULAR", item.titular)
        intent.putExtra("DESC", item.nota)
        //startActivity(intent)
        startActivityForResult(intent, newWordActivityRequestCode)

*/

    }


}