package com.theduc.educationapplication.ui.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class SignUpViewModel() : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()


    fun signUp(email: String, password: String, listener: OnSignUpListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    listener.onSignUpSuccess()
                } else {
                    listener.onSignUpFailure(task.exception?.message ?: "Unknown error")
                }
            }
    }


    interface OnSignUpListener {
        fun onSignUpSuccess()
        fun onSignUpFailure(error: String)
    }

}

