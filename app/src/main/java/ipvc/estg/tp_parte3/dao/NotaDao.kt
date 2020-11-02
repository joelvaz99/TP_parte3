package ipvc.estg.tp_parte3.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("UPDATE nota_table SET nota=:nota WHERE titular = :titular")
    fun updateNota(titular:String, nota: String)

}