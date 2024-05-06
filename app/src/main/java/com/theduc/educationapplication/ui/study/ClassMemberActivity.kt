package com.theduc.educationapplication.ui.study

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

class ClassMemberActivity : AppCompatActivity() {

    private lateinit var textViewStudentCount: TextView
    private lateinit var membersRecyclerView: RecyclerView
    private lateinit var membersAdapter: ClassMemberAdapter
    private var membersList: MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_class)

        textViewStudentCount = findViewById(R.id.textViewStudentCount)
        membersRecyclerView = findViewById(R.id.rv_students) // Assuming RecyclerView with this ID

        membersAdapter = ClassMemberAdapter(this, membersList)
        membersRecyclerView.layoutManager = LinearLayoutManager(this)
        membersRecyclerView.adapter = membersAdapter

        fetchStudentNames()


    }

    private fun fetchStudentNames() {
        val memberRef = FirebaseDatabase.getInstance().getReference("classMember")
        memberRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                membersList.clear() // Clear existing data before adding new students
                for (snapshot in dataSnapshot.children) {
                    val member = snapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                    val memberName = member?.get("name") as String
                    membersList.add(Student(index = membersList.size + 1, name = memberName))
                }
                membersAdapter.notifyDataSetChanged() // Update adapter with new data
                // Cập nhật số lượng sinh viên
                textViewStudentCount.text = "${membersList.size} sinh viên"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ClassMemberActivity", "Failed to fetch student names: ${databaseError.message}")
            }
        })
    }




    private fun cancelActivity() {
        val cancelIntent = Intent(this@ClassMemberActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}


