package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityAutenticBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.AutenticFirebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AutenticActivity: AppCompatActivity() {
    private lateinit var activityAutenticBinding: ActivityAutenticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAutenticBinding = ActivityAutenticBinding.inflate(layoutInflater)
        setContentView(activityAutenticBinding.root)

    }

    fun onClick(view: View) {
        when (view) {
            activityAutenticBinding.cadastrarBt -> {
                startActivity(Intent(this, CadastrarActivity::class.java))
            }

            activityAutenticBinding.entrarBt -> {
                val email: String = activityAutenticBinding.emailEt.text.toString()
                val senha: String = activityAutenticBinding.senhaEt.text.toString()
                AutenticFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha)
                    .addOnSuccessListener {
                        loginSucesso()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

            activityAutenticBinding.recuperarSenhaBt -> {
                startActivity(Intent(this, RecuperarSenhaActivity::class.java))

            }
        }
    }

    private fun loginSucesso() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}