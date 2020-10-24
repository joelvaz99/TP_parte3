package ipvc.estg.tp_parte3.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.tp_parte3.db.NotaDB
import ipvc.estg.tp_parte3.dao.NotaDao
import ipvc.estg.tp_parte3.db.NotaRepository
import ipvc.estg.tp_parte3.entities.Nota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotaRepository

    val allNotas: LiveData<List<Nota>>

    init {
        val notasDao = NotaDB.getDatabase(application,viewModelScope).notaDao()// cityDao
        repository = NotaRepository(notasDao)
        allNotas = repository.allNotas
    }


    fun insert(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(nota)
    }




}