package br.edu.ifsp.scl.ads.pdm.listatarefas.model

import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaFirebase.Constantes.TAREFA_DATABASE
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaDAO
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class TarefaFirebase: TarefaDAO {

    object Constantes {
        val TAREFA_DATABASE = "listaTarefa"
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
    }

    override fun createTarefa(tarefa: Tarefa) = createOrUpdadeTarefa(tarefa)

    override fun readTarefa(nome: String): Tarefa = tarefasList[tarefasList.indexOfFirst { it.usuario.equals(nome) }]

    override fun readTarefa(): MutableList<Tarefa> = tarefasList

    override fun updateTarefa(tarefa: Tarefa) = createOrUpdadeTarefa(tarefa)

    override fun deleteTarefa(nome: String) {
        tarefasListaRtDb.child(nome).removeValue()
    }

    private fun createOrUpdadeTarefa(tarefa: Tarefa) {
        tarefasListaRtDb.child(tarefa.usuario).setValue(tarefa)
    }

}