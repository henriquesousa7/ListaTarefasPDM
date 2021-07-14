package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityVisualizarTarefaBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import java.text.SimpleDateFormat

class VisualizarTarefaActivity : AppCompatActivity () {
    private lateinit var activityVisualizarTarefaBinding: ActivityVisualizarTarefaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityVisualizarTarefaBinding = ActivityVisualizarTarefaBinding.inflate(layoutInflater)
        setContentView(activityVisualizarTarefaBinding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val tarefa: Tarefa? = bundle.getParcelable("Tarefa")
            if (tarefa != null) {
                with(activityVisualizarTarefaBinding) {
                    tituloTarefaTv.setText("Titulo: " + tarefa.titulo)
                    descricaoTarefaTv.setText("Descricao; " + tarefa.descricao)
                    usuarioCriadorTarefaTv.setText("Criador: " + tarefa.usuario)
                    dataCriacaoTarefaTv.setText("Data criacao: " + tarefa.dataCriacao)
                    dataPrevistaTarefaTv.setText("Data prevista: " + tarefa.dataPrevista)
                    statusTarefaTv.setText("Status: " + tarefa.statusTarefa)
                    usuarioConcluiuTarefaTv.setText("Usuario concluiu: " + tarefa.usuarioConcluiu)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (AutenticFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }
}