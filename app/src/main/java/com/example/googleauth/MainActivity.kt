package com.example.googleauth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {

    lateinit var gso: GoogleSignInOptions;
    lateinit var gsc: GoogleSignInClient;

    lateinit var googleSignBtn: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            navigateToHomePage();
        }

        googleSignBtn = findViewById(R.id.googleSignBtn);

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        gsc = GoogleSignIn.getClient(this, gso);


        //create contract between between two activity so here first one is your main activity and other is google sign activity
        val contract = object : ActivityResultContract<Intent, Intent>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input;
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Intent {
                return intent!!;
            }
        }

        val launcher = registerForActivityResult(
            contract
        ) { result ->
            val googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(result);
            if (googleSignInAccount.isSuccessful) {
                navigateToHomePage();
            }
        }

        googleSignBtn.setOnClickListener {
            launcher.launch(gsc.signInIntent);
        }
    }

    private fun navigateToHomePage() {
        finish()
        startActivity(Intent(this, HomeActivity::class.java));
    }


}