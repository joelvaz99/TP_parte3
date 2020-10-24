package ipvc.estg.tp_parte3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.tp_parte3.dao.NotaDao
import ipvc.estg.tp_parte3.entities.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Database(entities = arrayOf(Nota::class), version = 6, exportSchema = false)
public abstract class NotaDB : RoomDatabase() {

    abstract fun notaDao(): NotaDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var notaDao=database.notaDao()


                        // Colocar duas cidades de Inicio
                        var nota = Nota(1, titular = "Joel", nota = "10" )
                    notaDao.insert(nota)

                    nota = Nota(2, titular = "Ana", nota = "12" )
                    notaDao.insert(nota)


                }
            }
        }
    }


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NotaDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotaDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotaDB::class.java,
                    "notas_database"
                )

                    //estrategia destruicao
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}