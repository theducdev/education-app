package com.theduc.educationapplication.ui.study

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
import com.theduc.educationapplication.data.model.Subject

class SubjectResultActivity : AppCompatActivity() {

    private lateinit var subjectRecyclerView: RecyclerView
    private lateinit var subjectAdapter: SubjectAdapter
    private var subjectList: MutableList<Subject> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_result)

        subjectRecyclerView = findViewById(R.id.rv_subject) // Assuming RecyclerView with this ID

        subjectAdapter = SubjectAdapter(this, subjectList)
        subjectRecyclerView.layoutManager = LinearLayoutManager(this)
        subjectRecyclerView.adapter = subjectAdapter

        fetchStudentNames()


    }

    private fun fetchStudentNames() {
        val memberRef = FirebaseDatabase.getInstance().getReference("subject")
        memberRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                subjectList.clear() // Clear existing data before adding new students

                for (snapshot in dataSnapshot.children) {
                    val subject = snapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                    val subjectName = subject?.get("name") as String
                    val subjectQuantity = subject?.get("quantity") as String
                    val subjectResult = subject?.get("result") as String

                    subjectList.add(Subject(subjectQuantity + " TC ", subjectName, subjectResult))

                }
                subjectAdapter.notifyDataSetChanged() // Update adapter with new data

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ClassMemberActivity", "Failed to fetch student names: ${databaseError.message}")
            }
        })
    }




    private fun cancelActivity() {
        val cancelIntent = Intent(this@SubjectResultActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}





