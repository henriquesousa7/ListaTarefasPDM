package br.edu.ifsp.scl.ads.pdm.listatarefas.controller

import br.edu.ifsp.scl.ads.pdm.listatarefas.MainActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaDAO
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaFirebase

class TarefaController (mainActivity: MainActivity) {
    val tarefaDao : TarefaDAO
    init {
        tarefaDao = TarefaFirebase(mainActivity)
    }

    fun insereTarefa(tarefa: Tarefa) = tarefaDao.createTarefa(tarefa)
    fun buscaTarefa(titulo: String) = tarefaDao.readTarefa(titulo)
    fun buscaTarefas() = tarefaDao.readTarefa()
    fun atualizaTarefa(tarefa: Tarefa) = tarefaDao.updateTarefa(tarefa)
    fun removeTarefa(titulo: String) = tarefaDao.deleteTarefa(titulo)
}