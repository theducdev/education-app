package com.theduc.educationapplication.ui.utilities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.News

class IntroduceActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce)
    }

    private fun cancelActivity() {
        val cancelIntent = Intent(this@IntroduceActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}

