package com.theduc.educationapplication.ui.study

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.Video
import com.theduc.educationapplication.data.model.VideoListAdapter
import java.io.File


class LibraryActivity : AppCompatActivity() {
    private lateinit var videoList: RecyclerView
    private lateinit var videoView: VideoView
    private var adapter: VideoListAdapter? = null // Adapter for video list, can be null initially

    // Sample video data (replace with actual data retrieval from Firebase Storage)
    private val videos = listOf(
        Video("Hệ thống phân tán, Xử lý dữ liệu truyền thống", "gs://education-application-2747e.appspot.com/video1.mp4"),
        Video("Video 2", "gs://education-application-2747e.appspot.com/video2.mp4")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        videoList = findViewById(R.id.video_list)
        videoView = findViewById(R.id.video_view)



        // Set up recycler view
        adapter = VideoListAdapter(videos) { video ->
            playVideo(video.url) // Call function to download and play video
        }
        videoList.adapter = adapter

        // Optional: Set layout manager for the recycler view
        videoList.layoutManager = LinearLayoutManager(this)
        Log.d("test", "tai thanh cong")
    }

    private fun playVideo(videoUrl: String) {
        // Download and play video logic (same as before)
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(videoUrl)
        val localFile = File.createTempFile("video", "mp4")
        storageRef.getFile(localFile).addOnSuccessListener {
            videoView.visibility = View.VISIBLE // Make video view visible
            videoView.setVideoURI(Uri.fromFile(localFile))
            videoView.start()
        }
    }
}