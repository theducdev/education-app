package com.theduc.educationapplication.ui.study

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.theduc.educationapplication.MainActivity

import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.News
import com.theduc.educationapplication.data.model.Student
import com.theduc.educationapplication.ui.utilities.ButtonAdapter

class SubjectMemberActivity : AppCompatActivity() {
    private lateinit var buttonListView:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_member)

        buttonListView = findViewById(R.id.buttonLV)

        val buttonNames = listOf("Cơ sở dữ liệu phân tán",
            "Nhập môn công nghệ phần mềm",
            "Lập trình web",
            "Nhập môn trí tuệ nhân tạo",
            "Cơ sở dữ liệu",
            "Lập trình với Python",
            "Hệ điều hành",
            "Lịch sử Đảng cộng sản Việt Nam") // Danh sách tên các nút


        val adapter = SubjectButtonAdapter(this, buttonNames)
        buttonListView.adapter = adapter


    }


    private fun cancelActivity() {
        val cancelIntent = Intent(this@SubjectMemberActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}

class SubjectButtonAdapter(context: Context, private val buttonNames: List<String>) :
    ArrayAdapter<String>(context, R.layout.button_item, buttonNames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.button_item, parent, false)
        }

        val buttonItem = view?.findViewById<Button>(R.id.buttonItem)
        val buttonName = buttonNames[position]
        buttonItem?.text = buttonName

        // Xử lý sự kiện khi người dùng nhấn vào nút
        buttonItem?.setOnClickListener {
            // Xử lý logic khi người dùng nhấn vào nút ở vị trí position
            when(position) {
                0 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                1 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                2 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                3 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                4 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                5 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                6 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }
                7 -> {
                    val subjectMemberIntent = Intent(context, CSDLPTActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }

            }

        }

        return view!!
    }

}
