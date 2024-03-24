package com.theduc.educationapplication.ui.personal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var matkhauhientaiEDT: EditText
    private lateinit var matkhaumoiEDT: EditText
    private lateinit var nhaplaimatkhaumoiEDT: EditText
    private lateinit var quaylaiBT: Button
    private lateinit var doimatkhauBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        matkhauhientaiEDT = findViewById(R.id.mat_khau_hien_tai)
        matkhaumoiEDT = findViewById(R.id.mat_khau_moi)
        nhaplaimatkhaumoiEDT = findViewById(R.id.nhap_lai_mat_khau_moi)
        quaylaiBT = findViewById(R.id.quay_lai)
        doimatkhauBT = findViewById(R.id.doi_mat_khau)

        quaylaiBT.setOnClickListener { cancelActivity() }
        doimatkhauBT.setOnClickListener { ChangePassword() }

    }

    private fun ChangePassword() {
        // Lấy người dùng hiện tại
        val user = FirebaseAuth.getInstance().currentUser

        // Lấy mật khẩu cũ từ EditText
        val oldPassword = matkhauhientaiEDT.text.toString()

        // Xác thực mật khẩu cũ
        val credential = EmailAuthProvider.getCredential(user!!.email!!, oldPassword)
        user.reauthenticate(credential)
            .addOnSuccessListener {
                // Mật khẩu cũ đã được xác thực thành công, tiếp tục thay đổi mật khẩu
                val newPassword = matkhaumoiEDT.text.toString()
                val confirmPassword = nhaplaimatkhaumoiEDT.text.toString()

                if (newPassword == confirmPassword) {
                    // Mật khẩu mới và mật khẩu nhập lại khớp nhau, tiếp tục thực hiện thay đổi mật khẩu
                    // Thay đổi mật khẩu
                    user.updatePassword(newPassword)
                        .addOnSuccessListener {
                            // Mật khẩu đã được thay đổi thành công
                            Toast.makeText(this, "Mật khẩu được thay đổi thành công", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            // Đã xảy ra lỗi trong quá trình thay đổi mật khẩu
                            Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()

                        }
                } else {
                    // Mật khẩu mới và mật khẩu nhập lại không khớp nhau, hiển thị thông báo lỗi cho người dùng
                    Toast.makeText(this, "Mật khẩu nhập lại không khớp với mật khẩu mới", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // Xác thực mật khẩu cũ không thành công, xử lý lỗi tại đây
                Toast.makeText(this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show()
            }

    }

    private fun cancelActivity() {
        val cancelIntent = Intent(this@ChangePasswordActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}

