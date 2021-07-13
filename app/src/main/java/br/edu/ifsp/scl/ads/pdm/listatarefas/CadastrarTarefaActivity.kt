package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityCadastrarTarefaBinding

class CadastrarTarefaActivity : AppCompatActivity() {
    private lateinit var activityCadastrarTarefaBinding: ActivityCadastrarTarefaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCadastrarTarefaBinding = ActivityCadastrarTarefaBinding.inflate(layoutInflater)
        setContentView(activityCadastrarTarefaBinding.root)
    }
}