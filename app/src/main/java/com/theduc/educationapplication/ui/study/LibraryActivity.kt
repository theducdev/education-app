package com.theduc.educationapplication.ui.study

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.Video
import com.theduc.educationapplication.data.model.VideoListAdapter


class LibraryActivity : AppCompatActivity() {
    private lateinit var videoList: RecyclerView
    private var adapter: VideoListAdapter? = null // Adapter for video list, can be null initially

    private lateinit var progressBar: ProgressBar
    private lateinit var videos: MutableList<Video>


    // Sample video data (replace with actual data retrieval from Firebase Storage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        videoList = findViewById(R.id.video_list)
        progressBar = findViewById(R.id.progress_bar)
        videos = mutableListOf()

        // Set up recycler view
        adapter = VideoListAdapter(videos) { video ->
            progressBar.visibility = View.VISIBLE
            playVideo(video.url) // Call function to play video
        }
        videoList.adapter = adapter

        // Optional: Set layout manager for the recycler view
        videoList.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase Database
        val database = FirebaseDatabase.getInstance()
        val videosRef = database.getReference().child("studyVideo") // Replace "videoStudy" with your actual node name


        // Add ValueEventListener to videosRef
        videosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (videoSnapshot in dataSnapshot.children) {
                    val videoData = videoSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                    val videoName = videoData?.get("name") as String
                    val videoUrl = videoData["link"] as String
                    videos.add(Video(videoName, videoUrl))
                }
                adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("LibraryActivity", "Error getting videos", databaseError.toException())
                Toast.makeText(this@LibraryActivity, "Failed to load videos", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun playVideo(videoUrl: String) {
        // Lấy WebView từ layout
        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE //
            }
        }
        // Tải trang web chứa video
        webView.loadUrl(videoUrl)
    }


    private fun cancelActivity() {
        val cancelIntent = Intent(this@LibraryActivity, MainActivity::class.java)
        startActivity(cancelIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelActivity()
    }


}