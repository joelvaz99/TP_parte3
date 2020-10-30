package ipvc.estg.tp_parte3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.tp_parte3.R
import ipvc.estg.tp_parte3.entities.Nota

class NotaAdapter internal constructor(
    val listener: RowClickListener
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

   // private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Nota>() // Cached copy of words

    inner class NotaViewHolder(itemView: View, val listener: RowClickListener) : RecyclerView.ViewHolder(itemView) {
        val tituloItemView: TextView = itemView.findViewById(R.id.titulo) //muda
        val subtituloItemView: TextView = itemView.findViewById(R.id.subtitulo)
        val deleteItemView: ImageButton = itemView.findViewById(R.id.delete)

        fun initialize(item: Nota, action:RowClickListener){
            tituloItemView.text = item.titular
            subtituloItemView.text = item.nota
            deleteItemView.setOnClickListener{
                action.onDeleteUserClickListener(item,adapterPosition)
            }
           // itemView.setOnClickListener{
            //    action.onItemClickListener(item,adapterPosition)
               // action.onDeleteUserClickListener(item,adapterPosition)
         //   }
            }


}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
  // val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
    val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
   return NotaViewHolder(inflater,listener)
}

override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
    val current = notas[position]
    holder.tituloItemView.text = current.titular
    holder.subtituloItemView.text = current.nota
    //holder.subtituloItemView.text = current.subtitulo data
    holder.initialize(notas.get(position),listener)
}
internal fun setNotas(notas: List<Nota>) {
 this.notas = notas
 notifyDataSetChanged()
}

override fun getItemCount() = notas.size

interface RowClickListener{
    fun onDeleteUserClickListener(item: Nota,position: Int)
 fun onItemClickListener(item: Nota,position: Int)
}

}