package br.edu.ifsp.scl.ads.pdm.listatarefas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.pdm.listatarefas.MainActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.R
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa

class TarefaAdapter(
        private val tarefasList: MutableList<Tarefa<Any?>>,
        private val onTarefaClickListener: OnTarefaClickListener
): RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {
    inner class TarefaViewHolder(viewTarefa: View): RecyclerView.ViewHolder(viewTarefa) {
        val tituloTv: TextView = viewTarefa.findViewById(R.id.tituloTarefaTv)
        val descricaoTv: TextView = viewTarefa.findViewById(R.id.descricaoTarefaTv)
    }

    private var posicao = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val viewTarefa: View = LayoutInflater.from(parent.context).inflate(R.layout.view_tarefa, parent, false)
        return TarefaViewHolder(viewTarefa)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa: Tarefa<Any?> = tarefasList[position]

        holder.tituloTv.text = tarefa.titulo
        holder.descricaoTv.text = tarefa.descricao
        holder.itemView.setOnClickListener {
            onTarefaClickListener.onTarefaClick(position)
        }
        holder.itemView.setOnLongClickListener { v: View? ->
            posicao = position
            false
        }
    }
    override fun getItemCount(): Int = tarefasList.size

    fun getPosicao(): Int {
        return posicao
    }

}