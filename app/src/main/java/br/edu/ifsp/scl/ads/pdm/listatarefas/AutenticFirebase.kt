package br.edu.ifsp.scl.ads.pdm.listatarefas

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

object AutenticFirebase {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    var googleSignInOptions: GoogleSignInOptions? = null
    var googleSignInClient: GoogleSignInClient? = null
    var googleSignInAccount: GoogleSignInAccount? = null
}