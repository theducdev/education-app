package com.theduc.educationapplication.ui.study

import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
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
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class LibraryActivity : AppCompatActivity() {
    private lateinit var videoList: RecyclerView
    private var adapter: VideoListAdapter? = null // Adapter for video list, can be null initially

    private lateinit var searchBar: EditText
    private lateinit var searchButton: Button

    private lateinit var addNewVideoButton: Button

    private lateinit var progressBar: ProgressBar

    private lateinit var curentVideos: MutableList<Video> // lưu video hiện tại hiển thị
    private lateinit var firebaseVideos: MutableList<Video> // lưu tất cả những video có trong firebase
    private lateinit var firebaseIdTeachers: MutableList<String> // lưu tất cả những idTeacher có trong firebase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        videoList = findViewById(R.id.video_list)
        progressBar = findViewById(R.id.progress_bar)

        searchBar = findViewById(R.id.search_bar)
        searchButton = findViewById(R.id.search_button)

        addNewVideoButton = findViewById(R.id.add_new_video_button)

        curentVideos = mutableListOf()
        firebaseVideos = mutableListOf()
        firebaseIdTeachers = mutableListOf()

        // Set up recycler view
        adapter = VideoListAdapter(curentVideos) { video ->
            progressBar.visibility = View.VISIBLE
            playVideo(video.url) // Call function to play video
        }
        videoList.adapter = adapter

        // Optional: Set layout manager for the recycler view
        videoList.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase Database
        val database = FirebaseDatabase.getInstance()
        val videosRef = database.getReference().child("studyVideo")

        val idTeacherRef = database.getReference().child("idTeacher")


        // Add ValueEventListener to videosRef
        videosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (videoSnapshot in dataSnapshot.children) {
                    val videoData = videoSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                    val videoName = videoData?.get("name") as String
                    val videoUrl = videoData["link"] as String
                    curentVideos.add(Video(videoName, videoUrl))
                }
                firebaseVideos.addAll(curentVideos)
                adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("LibraryActivity", "Error getting videos", databaseError.toException())
                Toast.makeText(this@LibraryActivity, "Failed to load videos", Toast.LENGTH_SHORT).show()
            }
        })

        // Xử lý sự kiện click nút Tìm kiếm
        searchButton.setOnClickListener {
            val searchText = searchBar.text.toString().trim()
            if (searchText.isNotEmpty()) {
                // Lọc danh sách video theo tên video
                val filteredVideos = firebaseVideos.filter { it.title.lowercase().contains(searchText.lowercase()) }

                if (filteredVideos.isEmpty()) {
                    Toast.makeText(this, "Không tìm thấy kết quả nào", Toast.LENGTH_SHORT).show()
                } else {
                    // Cập nhật adapter với danh sách video đã lọc
                    adapter?.updateVideos(filteredVideos)
                }
            }
        }

        // Add ValueEventListener to videosRef
        idTeacherRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (idSnapshot in dataSnapshot.children) {
                    val idData = idSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                    val id = idData?.get("id") as String
                    firebaseIdTeachers.add(id)
                }
                Log.d("test", firebaseIdTeachers.toString())
                adapter?.notifyDataSetChanged()

                val currentUser = FirebaseAuth.getInstance().currentUser
                Log.d("currentUser", currentUser?.uid.toString())

                if (currentUser != null && currentUser.uid in firebaseIdTeachers) {
                    addNewVideoButton.visibility = View.VISIBLE
                }else {
                    addNewVideoButton.visibility = View.GONE

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("LibraryActivity", "Error getting videos", databaseError.toException())
                Toast.makeText(this@LibraryActivity, "Failed to load videos", Toast.LENGTH_SHORT).show()
            }
        })

        // Xử lý sự kiện click nút Thêm Video
        addNewVideoButton.setOnClickListener { showAddVideoDialog() }
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

    private fun showAddVideoDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_video, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Thêm video mới")
            .setPositiveButton("Thêm", null)
            .setNegativeButton("Hủy", null)

        val dialog = dialogBuilder.create()
        dialog.show()

        // Lấy các view từ dialog
        val subjectEditText = dialogView.findViewById<EditText>(R.id.subject_edit_text)
        val titleEditText = dialogView.findViewById<EditText>(R.id.title_edit_text)
        val idVideoEditText = dialogView.findViewById<EditText>(R.id.link_edit_text)

        // Xử lý sự kiện click nút "Thêm"
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val subject = subjectEditText.text.toString().trim()
            val title = titleEditText.text.toString().trim()
            val idVideo = idVideoEditText.text.toString().trim()

            if (subject.isNotEmpty() && title.isNotEmpty() && idVideo.isNotEmpty()) {
                // Tạo video mới
                val video = Video("[$subject]" +" "+ title, getYoutubeEmbedLink(idVideo))

                // Lưu video vào Firebase
                addVideoToFirebase(video)

                // Ẩn dialog
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addVideoToFirebase(video: Video) {
        // Lấy database reference
        val database = FirebaseDatabase.getInstance()
        val videosRef = database.getReference().child("studyVideo")

        // Tạo map để lưu dữ liệu video
        val videoData = mapOf(
            "name" to video.title,
            "link" to video.url,
        )

        // Lưu dữ liệu video vào Firebase
        videosRef.push().setValue(videoData)

        // Hiển thị thông báo
        Toast.makeText(this, "Đã thêm video thành công", Toast.LENGTH_SHORT).show()
    }

    fun getYoutubeEmbedLink(idVideo: String): String {
        // Tạo link embed
        val embedLink = "https://www.youtube.com/embed/$idVideo"
        return embedLink
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