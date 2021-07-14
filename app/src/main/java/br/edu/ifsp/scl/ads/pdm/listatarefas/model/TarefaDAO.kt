package br.edu.ifsp.scl.ads.pdm.listatarefas.model

interface TarefaDAO {
    fun createTarefa(tarefa : Tarefa<Any?>)
    fun readTarefa(nome : String) : Tarefa<Any?>;
    fun readTarefa() : MutableList<Tarefa<Any?>>
    fun updateTarefa(tarefa : Tarefa<Any?>)
    fun deleteTarefa(nome : String)
}