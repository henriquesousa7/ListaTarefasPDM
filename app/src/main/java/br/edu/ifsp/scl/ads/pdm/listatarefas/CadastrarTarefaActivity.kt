package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityCadastrarTarefaBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa

class CadastrarTarefaActivity : AppCompatActivity() {
    private lateinit var activityCadastrarTarefaBinding: ActivityCadastrarTarefaBinding

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
                usuarioEt.text.toString(),
                dataCriacaoET.text.toString(),
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