package br.edu.ifsp.scl.ads.pdm.listatarefas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityCadastrarBinding

class CadastrarActivity: AppCompatActivity() {
    private lateinit var activityCadastrarBinding: ActivityCadastrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCadastrarBinding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(activityCadastrarBinding.root)
    }

    fun onClick(view: View) {
        if (view == activityCadastrarBinding.cadastrarBt) {
            val email = activityCadastrarBinding.emailEt.text.toString()
            val senha = activityCadastrarBinding.senhaEt.text.toString()
            val repetirSenha = activityCadastrarBinding.repetirSenhaEt.text.toString()

            if (checkValores(email, senha, repetirSenha)) {
                AutenticFirebase.firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { conta ->
                    if (conta.isSuccessful) {
                        Toast.makeText(this, "Conta criada com sucesso.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Erro na criação da conta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "Dados invalidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValores(email: String, senha: String, repetirSenha: String): Boolean {
        return if (email.isBlank() || email.isEmpty())
            false
        else if (senha.isBlank() || senha.isEmpty())
            false
        else if (repetirSenha.isBlank() || repetirSenha.isEmpty())
            false
        else senha.equals(repetirSenha)
    }
}