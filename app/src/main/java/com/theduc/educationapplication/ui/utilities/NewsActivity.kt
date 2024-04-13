package com.theduc.educationapplication.ui.utilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.News
import com.theduc.educationapplication.data.model.Video

class NewsActivity : AppCompatActivity() {

    private lateinit var newsListView: ListView
    private lateinit var newsList: MutableList <News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsListView = findViewById(R.id.tin_tuc_listview)
        newsList = mutableListOf<News>()

        val newsRef = FirebaseDatabase.getInstance().getReference("news")

        newsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {

                    val news = snapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                    val titleNews = news?.get("title") as String
                    val contentNews = news["content"] as String
                    newsList.add(News(titleNews, contentNews))

                }

                // Hiển thị dữ liệu trong ListView hoặc RecyclerView
                val adapter = ArrayAdapter(this@NewsActivity,android.R.layout.simple_list_item_1, newsList.map { it.title } )
                newsListView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "onCancelled", databaseError.toException())
            }
        })

        newsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedNews = newsList[position]
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_TIN_TUC, selectedNews)
            startActivity(intent)
        }

    }


    private fun cancelActivity() {
        val cancelIntent = Intent(this@NewsActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }
}

