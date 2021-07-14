package br.edu.ifsp.scl.ads.pdm.listatarefas.model

interface TarefaDAO {
    fun createTarefa(tarefa : Tarefa)
    fun readTarefa(nome : String) : Tarefa;
    fun readTarefa() : MutableList<Tarefa>
    fun updateTarefa(tarefa : Tarefa)
    fun deleteTarefa(nome : String)
}