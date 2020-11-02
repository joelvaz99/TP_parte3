package ipvc.estg.tp_parte3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "nota_table")

class Nota(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "titular") val titular: String,
    @ColumnInfo( name = "nota") val nota: String,
    @ColumnInfo( name = "data") val data: String
    // adicionar data
) {

}