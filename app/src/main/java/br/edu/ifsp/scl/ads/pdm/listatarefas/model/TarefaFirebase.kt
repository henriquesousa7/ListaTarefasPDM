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


    private val tarefasList: MutableList<Tarefa<Any?>> = mutableListOf()

    init {
        tarefasListaRtDb.keepSynced(true)
        tarefasListaRtDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTarefa: Tarefa<Any?> = snapshot.getValue<Tarefa<Any?>>()?:Tarefa()
                if (tarefasList.indexOfFirst { it.usuario.equals(novaTarefa.usuario) } == -1) {
                    tarefasList.add(novaTarefa)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefaEditada: Tarefa<Any?> = snapshot.getValue<Tarefa<Any?>>()?:Tarefa()
                val indice = tarefasList.indexOfFirst { it.usuario.equals(tarefaEditada.usuario) }
                tarefasList[indice] = tarefaEditada
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tarefaRemovida: Tarefa<Any?> = snapshot.getValue<Tarefa<Any?>>()?:Tarefa()
                tarefasList.remove(tarefaRemovida)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        tarefasListaRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tarefasList.clear()
                for (c in snapshot.children) {
                    val tarefa: Tarefa<Any?> = (c.getValue<Tarefa<Any?>>() ?: Tarefa())
                    tarefasList.add(tarefa)
                    if (mainActivity != null) {
                        mainActivity.tarefasList.add(tarefa)
                        mainActivity.tarefasAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun createTarefa(tarefa: Tarefa<Any?>) = createOrUpdadeTarefa(tarefa)

    override fun readTarefa(titulo: String): Tarefa<Any?> = tarefasList[tarefasList.indexOfFirst { it.titulo == titulo }]

    override fun readTarefa(): MutableList<Tarefa<Any?>> = tarefasList

    override fun updateTarefa(tarefa: Tarefa<Any?>) = createOrUpdadeTarefa(tarefa)

    override fun deleteTarefa(titulo: String) {
        tarefasListaRtDb.child(titulo).removeValue()
    }

    private fun createOrUpdadeTarefa(tarefa: Tarefa<Any?>) {
        tarefasListaRtDb.child(tarefa.titulo).setValue(tarefa)
    }

}