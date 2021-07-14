package br.edu.ifsp.scl.ads.pdm.listatarefas.controller

import br.edu.ifsp.scl.ads.pdm.listatarefas.MainActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaDAO
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.TarefaFirebase

class TarefaController (mainActivity: MainActivity) {
    val tarefaDao : TarefaDAO
    init {
        tarefaDao = TarefaFirebase()
    }

    fun insereTarefa(tarefa: Tarefa) = tarefaDao.createTarefa(tarefa)
    fun buscaTarefa(nome: String) = tarefaDao.readTarefa(nome)
    fun buscaTarefas() = tarefaDao.readTarefa()
    fun atualizaTarefa(tarefa: Tarefa) = tarefaDao.updateTarefa(tarefa)
    fun removeTarefa(nome: String) = tarefaDao.deleteTarefa(nome)
}