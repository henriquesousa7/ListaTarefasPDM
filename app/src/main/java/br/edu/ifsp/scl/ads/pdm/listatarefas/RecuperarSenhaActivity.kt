package br.edu.ifsp.scl.ads.pdm.listatarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.pdm.listatarefas.databinding.ActivityRecuperarSenhaBinding
import br.edu.ifsp.scl.ads.pdm.listatarefas.AutenticFirebase

class RecuperarSenhaActivity : AppCompatActivity() {
    // View binding
    private lateinit var activityRecuperarSenhaBinding: ActivityRecuperarSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecuperarSenhaBinding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(activityRecuperarSenhaBinding.root)
    }

    fun onClick(view: View) {
        if (view == activityRecuperarSenhaBinding.enviarEmailBt) {
            val email = activityRecuperarSenhaBinding.emailRecuperacaoSenhaEt.text.toString()
            if (email.isNotBlank() && email.isNotEmpty()) {
                AutenticFirebase.firebaseAuth.sendPasswordResetEmail(email)
                Toast.makeText(this, "E-mail de recuperação enviado", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}