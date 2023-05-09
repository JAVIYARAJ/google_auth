package com.example.googleauth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_acitivity)

        var image=findViewById<ImageView>(R.id.userProfilePic)
        val username = findViewById<TextView>(R.id.username)
        val email = findViewById<TextView>(R.id.userEmail)
        val logout = findViewById<Button>(R.id.logout);

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        val gsc = GoogleSignIn.getClient(this, gso);

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null) {
            Toast.makeText(this,googleSignInAccount.photoUrl.toString(),Toast.LENGTH_SHORT).show()
            Glide.with(this).load(googleSignInAccount.photoUrl).into(image);
            username.text = googleSignInAccount.displayName;
            email.text = googleSignInAccount.email;
        }

        logout.setOnClickListener {
            gsc.signOut();
            finish()
            startActivity(Intent(this, MainActivity::class.java));
        }
    }
}