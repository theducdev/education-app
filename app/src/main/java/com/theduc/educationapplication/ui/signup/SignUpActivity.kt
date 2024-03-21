package com.theduc.educationapplication.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.theduc.educationapplication.R
import com.theduc.educationapplication.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        emailEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        signUpButton = findViewById(R.id.signupBT)
        loginButton = findViewById(R.id.loginBT)

        signUpButton.setOnClickListener { createAccount() }
        loginButton.setOnClickListener { changeToLogin() }
    }

    private fun createAccountInFirebase(email: String, password: String) {
        signUpViewModel.signUp(email, password, object : SignUpViewModel.OnSignUpListener {
            override fun onSignUpSuccess() {
                // Đăng ký thành công, thực hiện hành động tiếp theo
                val user = FirebaseAuth.getInstance().currentUser
                user?.sendEmailVerification()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@SignUpActivity, "Verification email sent.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@SignUpActivity, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                        }
                    }
                // Chuyển đến activity hoặc thực hiện hành động khác
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            override fun onSignUpFailure(error: String) {
                // Đăng ký thất bại, hiển thị thông báo lỗi
                Toast.makeText(this@SignUpActivity, "Sign up failed: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun changeToLogin() {
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun createAccount() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        val isValided = validateData(email, password, confirmPassword)
        if (!isValided) {
            return
        }
        createAccountInFirebase(email, password)
    }

    fun validateData(email: String?, password: String, confirmPassword: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email is invalid")
            return false
        }
        if (password.length < 6) {
            passwordEditText.setError("Password length is invalid")
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordEditText.setError("Password not matched")
            return false
        }
        return true
    }
}

