package ipvc.estg.tp_parte3.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.estg.tp_parte3.entities.Nota

@Dao
interface NotaDao {

    //Inserir
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Nota) // Vai ser Chamado no Repositorio

    //Selecionar tudo
    @Query("SELECT * from nota_table ORDER BY titular ASC")
    fun getAllNotas(): LiveData<List<Nota>>


    //Delete
    @Query("DELETE FROM nota_table WHERE titular=:titular ")
    suspend fun deleteNota(titular:String)

}