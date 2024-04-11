package com.healthcare

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.healthcare.models.User

class Register : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var editTextName: EditText? = null
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextName = findViewById(R.id.editTextName)
        editTextPassword = findViewById(R.id.editTextPassword)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Registering...")
    }

    fun signUp(view: View?) {
        val email = editTextEmail?.text.toString().trim()
        val name = editTextName?.text.toString().trim()
        val password = editTextPassword?.text.toString().trim()

        if (email.isNullOrEmpty() || name.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog?.show()

        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                progressDialog?.dismiss()
                if (task.isSuccessful) {
                    // Sign up success
                    Toast.makeText(
                        this@Register, "Sign up successful. Log in to proceed.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val user = mAuth?.currentUser
                    user?.let {
                        val userId = it.uid
                        saveUserIdToSharedPreferences(userId)
                        val newUser = User(name, email)
                        val usersRef: DatabaseReference =
                            FirebaseDatabase.getInstance().reference.child("healthcare/users")
                        usersRef.child(userId).setValue(newUser)
                    }

                    startActivity(Intent(this@Register, Login::class.java))
                    finish()
                } else {
                    // Sign up failed
                    Toast.makeText(
                        this@Register, "Sign up failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserIdToSharedPreferences(userId: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.apply()
    }

    fun logIn(view: View?) {
        startActivity(Intent(this@Register, Login::class.java))
        finish()
    }
}