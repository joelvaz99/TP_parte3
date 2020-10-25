package ipvc.estg.tp_parte3.db

import androidx.lifecycle.LiveData
import ipvc.estg.tp_parte3.dao.NotaDao
import ipvc.estg.tp_parte3.entities.Nota

//Vai Chamar o metodo correspondente no DAO
class NotaRepository(private val notaDao: NotaDao) {

    //Listar
    val allNotas: LiveData<List<Nota>> = notaDao.getAllNotas()

   // Inserir
    suspend fun insert(nota: Nota) {
        notaDao.insert(nota)
    }

}