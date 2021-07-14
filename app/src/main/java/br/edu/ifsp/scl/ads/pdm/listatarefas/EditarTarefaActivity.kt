package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityEditarTarefaBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import java.text.SimpleDateFormat

class EditarTarefaActivity : AppCompatActivity () {
    private lateinit var activityEditarTarefaBinding: ActivityEditarTarefaBinding
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var  tarefaBase : Tarefa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditarTarefaBinding = ActivityEditarTarefaBinding.inflate(layoutInflater)
        setContentView(activityEditarTarefaBinding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val tarefa: Tarefa? = bundle.getParcelable("editTarefa")
            if (tarefa != null) {
                tarefaBase = tarefa
                with(activityEditarTarefaBinding) {
                    descricaoEt.setText(tarefa.descricao)
                    dataPrevistaET.setText(tarefa.dataPrevista)
                }
            }
        }
    }

    fun onClick(view: View) {
        val tarefa: Tarefa
        with(activityEditarTarefaBinding) {
            tarefa = Tarefa(
                    tarefaBase.titulo,
                    descricaoEt.text.toString(),
                    tarefaBase.usuario,
                    tarefaBase.dataCriacao,
                    dataPrevistaET.text.toString(),
                    tarefaBase.statusTarefa,
                    tarefaBase.usuarioConcluiu
            )
        }
        val retornoIntent = Intent()
        when (view) {
            activityEditarTarefaBinding.editarTarefaBt -> {
                retornoIntent.putExtra(Intent.EXTRA_USER, tarefa)
                setResult(RESULT_OK, retornoIntent)
                finish()
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