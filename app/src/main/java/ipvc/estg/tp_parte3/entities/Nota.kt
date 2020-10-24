package ipvc.estg.tp_parte3.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nota_table")

class Nota(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "titular") val titular: String,
    @ColumnInfo( name = "nota") val nota: String
)