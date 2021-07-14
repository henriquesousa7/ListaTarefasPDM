package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.controller.TarefaController
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityCadastrarTarefaBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import java.text.SimpleDateFormat
import java.util.*

class CadastrarTarefaActivity : AppCompatActivity() {
    private lateinit var activityCadastrarTarefaBinding: ActivityCadastrarTarefaBinding
    private lateinit var tarefaController: TarefaController
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd/M/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCadastrarTarefaBinding = ActivityCadastrarTarefaBinding.inflate(layoutInflater)
        setContentView(activityCadastrarTarefaBinding.root)
    }

    fun onClick(view: View){
        val tarefa: Tarefa
        with(activityCadastrarTarefaBinding) {
            tarefa = Tarefa(
                    tituloEt.text.toString(),
                    descricaoEt.text.toString(),
                    AutenticFirebase.firebaseAuth.currentUser?.email.toString(),
                    sdf.format(Date()),
                    dataPrevistaET.text.toString()
            )
        }

        if (view == activityCadastrarTarefaBinding.adicionarTarefaBt) {
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, tarefa)
            setResult(AppCompatActivity.RESULT_OK, retornoIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if(AutenticFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }
}