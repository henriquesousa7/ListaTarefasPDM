package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityAutenticBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.AutenticFirebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class AutenticActivity: AppCompatActivity() {
    private lateinit var activityAutenticBinding: ActivityAutenticBinding
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAutenticBinding = ActivityAutenticBinding.inflate(layoutInflater)
        setContentView(activityAutenticBinding.root)

        AutenticFirebase.googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()

        AutenticFirebase.googleSignInClient =
                GoogleSignIn.getClient(this, AutenticFirebase.googleSignInOptions!!)

        AutenticFirebase.googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

        if (AutenticFirebase.googleSignInAccount != null) {
            loginSucesso()
        }

        activityAutenticBinding.entrarGoogleBt.setOnClickListener {
            val googleSignInIntent = AutenticFirebase.googleSignInClient?.signInIntent
            googleSignInLauncher.launch(googleSignInIntent)
        }

        googleSignInLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    val task: Task<GoogleSignInAccount> =
                            GoogleSignIn.getSignedInAccountFromIntent(result.data)

                    try {
                        AutenticFirebase.googleSignInAccount =
                                task.getResult(ApiException::class.java)

                        val credential: AuthCredential = GoogleAuthProvider.getCredential(
                                AutenticFirebase.googleSignInAccount?.idToken,
                                null
                        )

                        AutenticFirebase.firebaseAuth.signInWithCredential(credential)
                                .addOnSuccessListener { loginSucesso() }
                                .addOnFailureListener {
                                    Toast.makeText(
                                            this,
                                            "Problema com autenticação Google1",
                                            Toast.LENGTH_SHORT
                                    ).show()
                                }
                    } catch (e: ApiException) {
                        Toast.makeText(this, "Problema com autenticação Google2", Toast.LENGTH_SHORT)
                                .show()

                    }
                }

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

    override fun onStart() {
        super.onStart()
        if (AutenticFirebase.firebaseAuth.currentUser != null) {
            Toast.makeText(this, "Usuário já logado", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun loginSucesso() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}