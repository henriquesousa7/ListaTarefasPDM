package br.edu.ifsp.scl.ads.pdm.listatarefas

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

object AutenticFirebase {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    var googleSignInClient: GoogleSignInClient? = null
}