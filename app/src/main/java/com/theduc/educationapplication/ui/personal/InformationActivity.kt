package com.theduc.educationapplication.ui.personal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import java.util.Calendar


class InformationActivity : AppCompatActivity() {
    private lateinit var hotenEDT: EditText
    private lateinit var ngaysinhEDT: EditText
    private lateinit var gioitinhNam: RadioButton
    private lateinit var gioitinhNu: RadioButton
    private lateinit var lopEDT: EditText
    private lateinit var khoaEDT: EditText
    private lateinit var diachiEDT: EditText
    private lateinit var sdtEDT: EditText
    private lateinit var emailEDT: EditText
    private lateinit var capnhatBT: Button
    private lateinit var quaylaiBT: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        hotenEDT = findViewById(R.id.ho_ten)
        ngaysinhEDT = findViewById(R.id.ngay_sinh)
        gioitinhNam = findViewById(R.id.gioi_tinh_nam)
        gioitinhNu = findViewById(R.id.gioi_tinh_nu)
        lopEDT = findViewById(R.id.lop)
        khoaEDT = findViewById(R.id.khoa)
        diachiEDT = findViewById(R.id.dia_chi)
        sdtEDT = findViewById(R.id.dien_thoai)
        emailEDT = findViewById(R.id.email)
        capnhatBT = findViewById(R.id.cap_nhat)
        quaylaiBT = findViewById(R.id.quay_lai)

        initInformation()
        ngaysinhEDT.setOnClickListener { pickDateDialog() }
        capnhatBT.setOnClickListener { updateInformation() }
        quaylaiBT.setOnClickListener { cancelActivity() }

    }


    private fun initInformation() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            Toast.makeText(this, "Lỗi: Không có người dùng hiện tại!", Toast.LENGTH_SHORT).show()
            return
        }
        val uid = firebaseUser.uid
        val database = FirebaseFirestore.getInstance()
        database.collection("users").document(uid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Lấy dữ liệu dạng Map<String, Any>
                    val dataMap = documentSnapshot.data
                    if (dataMap != null) {
                        // Sử dụng dữ liệu ở đây
                        // Ví dụ: Lấy giá trị của một trường cụ thể
                        val hoten = dataMap["hoten"] as String
                        val ngaysinh = dataMap["ngaysinh"] as String
                        val diachi = dataMap["diachi"] as String
                        val sdt = dataMap["sdt"] as String
                        val email = dataMap["email"] as String
                        val lop = dataMap["lop"] as String
                        val khoa = dataMap["khoa"] as String
                        val gioitinh = dataMap["gioitinh"] as String

                        hotenEDT.setText(hoten)
                        ngaysinhEDT.setText(ngaysinh)
                        diachiEDT.setText(diachi)
                        sdtEDT.setText(sdt)
                        emailEDT.setText(email)
                        lopEDT.setText(lop)
                        khoaEDT.setText(khoa)

                        if (gioitinh.equals("nam")) gioitinhNam.isChecked = true
                        else gioitinhNu.isChecked = true
                    }
                } else {
                    Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Xử lý khi truy vấn thất bại
            }

    }

    private fun updateInformation() {

        val hoten = hotenEDT.text.toString()
        val ngaysinh = ngaysinhEDT.text.toString()
        val diachi = diachiEDT.text.toString()
        val sdt = sdtEDT.text.toString()
        val email = emailEDT.text.toString()
        val lop = lopEDT.text.toString()
        val khoa = khoaEDT.text.toString()
        val gioitinhnam = gioitinhNam.isChecked
        // Lấy database Firebase
        val database = FirebaseFirestore.getInstance()
        // Lấy uid người dùng hiện tại
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            Toast.makeText(this, "Lỗi: Không có người dùng hiện tại!", Toast.LENGTH_SHORT).show()
            return
        }
        val uid = firebaseUser.uid

        // Create reference to user's node using UID
        val userRef = database.collection("users").document(uid)

        // Create HashMap to store user information
        val user = HashMap<String, Any>()
        user["hoten"] = hoten
        user["ngaysinh"] = ngaysinh
        user["diachi"] = diachi
        user["sdt"] = sdt
        user["email"] = email
        user["lop"] = lop
        user["khoa"] = khoa
        user["gioitinh"] = if (gioitinhnam) "nam" else "nu"

        // Update user data
        userRef.set(user)
            .addOnSuccessListener {
                // Update successful
                Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                // Update failed
                Toast.makeText(this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show()
            }

    }

    private fun pickDateDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val ngaySinh = "${dayOfMonth}/${monthOfYear + 1}/${year}"
                ngaysinhEDT.setText(ngaySinh)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun cancelActivity() {
        val cancleIntent = Intent(this@InformationActivity, MainActivity::class.java)
        startActivity(cancleIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }

}



