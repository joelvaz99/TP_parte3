package ipvc.estg.tp_parte3.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.tp_parte3.db.NotaDB
import ipvc.estg.tp_parte3.db.NotaRepository
import ipvc.estg.tp_parte3.entities.Nota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//Vai chamar o repositorio
class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotaRepository

    val allNotas: LiveData<List<Nota>>

    init {
        //Indicar qual é o DAO
        val notasDao = NotaDB.getDatabase(application,viewModelScope).notaDao()// inicializar a base de dados
        repository = NotaRepository(notasDao)
        allNotas = repository.allNotas //Buscar todas as notas no repositorio
    }


    //Vai ao repositorio fazer o insert
    fun insert(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(nota)
    }

   fun deleteNota(titular: String) = viewModelScope.launch(Dispatchers.IO) {
      repository.deleteNota(titular)
   }

    fun updateNota(titular: String,nota:String) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNota(titular,nota)
    }





}