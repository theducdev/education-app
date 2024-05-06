package com.theduc.educationapplication.ui.utilities

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
import com.theduc.educationapplication.MainActivity

import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.News

class FeedbackActivity : AppCompatActivity() {

    private lateinit var contentEDT: EditText
    private lateinit var sendBT: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        contentEDT = findViewById(R.id.content_edit_text)
        sendBT = findViewById(R.id.send_button)

        sendBT.setOnClickListener { sendFeedback(contentEDT.text.toString()) }
    }

    private fun sendFeedback(text: String) {
        // Create Intent to send email
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Sử dụng ACTION_SENDTO và thiết lập URI mailto:
            putExtra(Intent.EXTRA_EMAIL, arrayOf("theduc.ng@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Phản hồi từ người dùng")
            putExtra(Intent.EXTRA_TEXT, text)
        }

        // Open email app chooser to send email
        startActivity(Intent.createChooser(intent, "Chọn ứng dụng email"))
    }


    private fun cancelActivity() {
        val cancelIntent = Intent(this@FeedbackActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}

