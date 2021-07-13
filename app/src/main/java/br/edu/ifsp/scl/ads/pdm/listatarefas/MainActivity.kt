package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.tarefaIt){
            startActivity(Intent(this, CadastrarTarefaActivity::class.java))
        }
        if(item.itemId == R.id.sairIt){
            Firebase.auth.signOut()
            startActivity(Intent(this, AutenticActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}