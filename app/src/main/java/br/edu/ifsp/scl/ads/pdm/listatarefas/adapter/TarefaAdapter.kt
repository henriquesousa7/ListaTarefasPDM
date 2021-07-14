package br.edu.ifsp.scl.ads.pdm.listatarefas.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.pdm.listatarefas.MainActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.R
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa

class TarefaAdapter(
        private val tarefasList: MutableList<Tarefa>,
        private val onTarefaClickListener: OnTarefaClickListener,
        private val menuInflater: MenuInflater
): RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {
    inner class TarefaViewHolder(viewTarefa: View): RecyclerView.ViewHolder(viewTarefa), View.OnCreateContextMenuListener {
        val tituloTv: TextView = viewTarefa.findViewById(R.id.tituloTarefaTv)
        val descricaoTv: TextView = viewTarefa.findViewById(R.id.descricaoTarefaTv)
        val statusTv: TextView = viewTarefa.findViewById(R.id.statusTarefaTv)
        init {
            viewTarefa.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?){
            menuInflater.inflate(R.menu.context_menu_tarefa, menu)
        }
    }

    private var posicao = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val viewTarefa: View = LayoutInflater.from(parent.context).inflate(R.layout.view_tarefa, parent, false)
        return TarefaViewHolder(viewTarefa)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa: Tarefa = tarefasList[position]

        holder.tituloTv.text = tarefa.titulo
        holder.descricaoTv.text = tarefa.descricao
        holder.statusTv.text = tarefa.statusTarefa
        holder.itemView.setOnClickListener {
            onTarefaClickListener.onTarefaClick(position)
        }
        holder.itemView.setOnLongClickListener {
            posicao = position
            false
        }
    }
    override fun getItemCount(): Int = tarefasList.size

    fun getPosicao(): Int {
        return posicao
    }

}