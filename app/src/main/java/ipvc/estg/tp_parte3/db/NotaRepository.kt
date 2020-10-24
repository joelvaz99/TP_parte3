package ipvc.estg.tp_parte3.db

import androidx.lifecycle.LiveData
import ipvc.estg.tp_parte3.dao.NotaDao
import ipvc.estg.tp_parte3.entities.Nota


class NotaRepository(private val notaDao: NotaDao) {

    //Listar
    val allNotas: LiveData<List<Nota>> = notaDao.getAllTitular()

   // Inserir
    suspend fun insert(nota: Nota) {
        notaDao.insert(nota)
    }

}