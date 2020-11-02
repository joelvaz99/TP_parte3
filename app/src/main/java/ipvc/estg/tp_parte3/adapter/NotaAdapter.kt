package ipvc.estg.tp_parte3.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.tp_parte3.R
import ipvc.estg.tp_parte3.entities.Nota
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NotaAdapter internal constructor(
    val listener: RowClickListener
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

   // private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Nota>() // Cached copy of words

    inner class NotaViewHolder(itemView: View, val listener: RowClickListener) : RecyclerView.ViewHolder(itemView) {
        val tituloItemView: TextView = itemView.findViewById(R.id.titulo) //muda
        val subtituloItemView: TextView = itemView.findViewById(R.id.subtitulo)
        val dataItemView: TextView = itemView.findViewById(R.id.data)
        val deleteItemView: ImageButton = itemView.findViewById(R.id.delete)
        val editItemView: ImageButton = itemView.findViewById(R.id.edit)


        fun initialize(item: Nota, action:RowClickListener){
            tituloItemView.text = item.titular
            subtituloItemView.text = item.nota
            deleteItemView.setOnClickListener{
                action.onDeleteUserClickListener(item,adapterPosition)
            }
            editItemView.setOnClickListener{
               action.onEditUserClickListener(item,adapterPosition)
           }
            }


}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
  // val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
    val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
   return NotaViewHolder(inflater,listener)
}

@RequiresApi(Build.VERSION_CODES.O)
override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
    val current = notas[position]
    val date = Calendar.getInstance().time
    val agora: LocalDateTime = LocalDateTime.now()
    val formatterData =
        DateTimeFormatter.ofPattern("dd/MM/uuuu")
    val dataFormatada = formatterData.format(agora)

    //holder.subtituloItemView.text = dateTimeFormat.format(date)
    holder.tituloItemView.text = current.titular
    holder.subtituloItemView.text = current.nota
    holder.dataItemView.text = current.data
    holder.initialize(notas.get(position),listener)
}
internal fun setNotas(notas: List<Nota>) {
 this.notas = notas
 notifyDataSetChanged()
}

override fun getItemCount() = notas.size

interface RowClickListener{
    fun onDeleteUserClickListener(item: Nota,position: Int)
 fun onEditUserClickListener(item: Nota,position: Int)
}

}