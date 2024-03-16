package com.theduc.educationapplication.data

import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.theduc.educationapplication.data.model.LoggedInUser
import java.io.IOException
import kotlin.concurrent.timerTask

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Đăng nhập thành công
                    val user = firebaseAuth.currentUser
                    val loggedInUser = LoggedInUser(user?.uid ?: "", user?.displayName ?: "")
                    callback(Result.Success(loggedInUser))
                } else {
                    // Đăng nhập thất bại
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        // Email không tồn tại hoặc đã bị xóa
                        callback(Result.Error(IOException("Email không tồn tại hoặc đã bị xóa")))
                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                        // Sai mật khẩu
                        callback(Result.Error(IOException("Sai mật khẩu")))
                    } else {
                        // Lỗi không xác định
                        callback(Result.Error(exception ?: IOException("Đăng nhập thất bại")))
                    }
                }
            }
    }


}