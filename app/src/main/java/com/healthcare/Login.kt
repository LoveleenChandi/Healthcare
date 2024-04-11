package com.healthcare

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var signInButton: Button? = null
    private var progressDialog: ProgressDialog? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        signInButton = findViewById(R.id.buttonSignIn)
        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Logging in...")

        signInButton?.setOnClickListener { _: View? -> signInUser() }
    }

    private fun signInUser() {
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }
        progressDialog!!.show()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                progressDialog!!.dismiss()
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        saveUserId(userId)
                        startActivity(Intent(this@Login, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@Login, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun register(view: View?) {
        startActivity(Intent(this@Login, Register::class.java))
        finish()
    }

    private fun saveUserId(userId: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString("user_id", userId)
        editor.apply()
    }
}