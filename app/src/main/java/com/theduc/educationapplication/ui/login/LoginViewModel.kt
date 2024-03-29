package com.theduc.educationapplication.ui.login

import android.content.Intent
import android.util.Patterns
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.LoginRepository
import com.theduc.educationapplication.data.Result


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // Gọi phương thức login từ LoginRepository
        loginRepository.login(username, password) { result ->
            // Xử lý kết quả đăng nhập
            if (result is Result.Success) {
                // Nếu đăng nhập thành công, cập nhật LiveData với kết quả thành công
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            } else {
                // Nếu đăng nhập thất bại, cập nhật LiveData với kết quả lỗi
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}