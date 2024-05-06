package com.theduc.educationapplication.ui.utilities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.News

class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TIN_TUC = "extra_tin_tuc"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        // Lấy tin tức từ Intent
        val news = intent.getParcelableExtra(EXTRA_TIN_TUC) as? News

        // Hiển thị thông tin tin tức trong layout
        news?.let {
            findViewById<TextView>(R.id.title_text_view).text = it.title
            findViewById<TextView>(R.id.content_text_view).text = it.content
        }

    }


    private fun cancelActivity() {
        val cancelIntent = Intent(this@DetailNewsActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}

