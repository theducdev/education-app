package com.theduc.educationapplication.ui.personal

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.theduc.educationapplication.data.LoginDataSource
import com.theduc.educationapplication.data.LoginRepository
import com.theduc.educationapplication.databinding.ActivityLoginBinding
import com.theduc.educationapplication.databinding.FragmentPersonalBinding
import com.theduc.educationapplication.ui.login.LoginActivity
import com.theduc.educationapplication.ui.login.LoginViewModel

class PersonalViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }
}