package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.ads.pdm.listatarefas.adapter.OnTarefaClickListener
import br.edu.ifsp.scl.ads.pdm.listatarefas.adapter.TarefaAdapter
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.model.Tarefa
import br.edu.ifsp.scl.ads.pdm.listatarefas.controller.TarefaController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnTarefaClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var tarefasList: MutableList<Tarefa>
    private lateinit var tarefasAdapter: TarefaAdapter
    private lateinit var tarefasLayoutManager: LinearLayoutManager

    private  lateinit var novaTarefaLauncher: ActivityResultLauncher<Intent>

    private lateinit var tarefaController: TarefaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        tarefaController = TarefaController(this)

        tarefasList = tarefaController.buscaTarefas()

        tarefasList = mutableListOf()

        tarefasAdapter = TarefaAdapter(tarefasList, this)
        activityMainBinding.tarefasRv.adapter = tarefasAdapter
        tarefasLayoutManager = LinearLayoutManager(this)
        activityMainBinding.tarefasRv.layoutManager = tarefasLayoutManager

        novaTarefaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val tarefa: Tarefa? = activityResult.data?.getParcelableExtra<Tarefa>(Intent.EXTRA_USER)
                if (tarefa != null) {
                    tarefasList.add(tarefa)
                    tarefasAdapter.notifyDataSetChanged()

                    tarefaController.insereTarefa(tarefa)

                }
            }
        }

    }

    override fun onTarefaClick(posicao: Int) {
        val tarefa: Tarefa = tarefasList[posicao]
        Toast.makeText(this, tarefa.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.tarefaMi -> {
            val novaTarefaIntent = Intent(this, CadastrarTarefaActivity::class.java)
            novaTarefaLauncher.launch(novaTarefaIntent)
            true
        }
        R.id.sairMi -> {
            AutenticFirebase.firebaseAuth.signOut()
            true
        }
        else -> {
            false
        }
    }

    override fun onStart() {
        super.onStart()
        if(AutenticFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }
}