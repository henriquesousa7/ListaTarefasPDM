package br.edu.ifsp.scl.ads.pdm.listatarefas.model

import br.edu.ifsp.scl.ads.pdm.listatarefas.MainActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaFirebase.Constantes.TAREFA_DATABASE
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class TarefaFirebase (mainActivity: MainActivity): TarefaDAO {

    object Constantes {
        val TAREFA_DATABASE = "listaTarefas"
    }

    private val tarefasListaRtDb = Firebase.database.getReference(TAREFA_DATABASE)


    private val tarefasList: MutableList<Tarefa> = mutableListOf()

    init {
        tarefasListaRtDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTarefa: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()
                if (tarefasList.indexOfFirst { it.usuario.equals(novaTarefa.usuario) } == -1) {
                    tarefasList.add(novaTarefa)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefaEditada: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()
                val indice = tarefasList.indexOfFirst { it.usuario.equals(tarefaEditada.usuario) }
                tarefasList[indice] = tarefaEditada
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tarefaRemovida: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()
                tarefasList.remove(tarefaRemovida)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        tarefasListaRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var tarefa: Tarefa = snapshot.getValue<Tarefa>()?:Tarefa()
                tarefasList.add(tarefa)
                mainActivity.tarefasList.add(tarefa)
                mainActivity.tarefasAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun createTarefa(tarefa: Tarefa) = createOrUpdadeTarefa(tarefa)

    override fun readTarefa(titulo: String): Tarefa = tarefasList[tarefasList.indexOfFirst { it.titulo == titulo }]

    override fun readTarefa(): MutableList<Tarefa> = tarefasList

    override fun updateTarefa(tarefa: Tarefa) = createOrUpdadeTarefa(tarefa)

    override fun deleteTarefa(titulo: String) {
        tarefasListaRtDb.child(titulo).removeValue()
    }

    private fun createOrUpdadeTarefa(tarefa: Tarefa) {
        tarefasListaRtDb.child(tarefa.titulo).setValue(tarefa)
    }

}