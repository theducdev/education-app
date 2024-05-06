package com.theduc.educationapplication.data.model

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.theduc.educationapplication.R

class VideoListAdapter(private val videos: MutableList<Video>, private val onVideoClick: (Video) -> Unit) :
    RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videos[position]
        holder.titleView.text = video.title

        // Tải ảnh thu nhỏ bằng Glide
        val thumbnailUrl = getYouTubeThumbnailUrl(video.url) // Giả sử thuộc tính 'url' chứa URL YouTube
        if (thumbnailUrl != null) {
            Glide.with(holder.itemView.context)
                .load(thumbnailUrl)
                .placeholder(R.drawable.placeholder_thumbnail) // Đặt ảnh giữ chỗ (tùy chọn)
                .error(R.drawable.error_thumbnail) // Đặt ảnh lỗi (tùy chọn)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // Kích hoạt lưu trữ đệm để tải nhanh hơn
                .into(holder.thumbnailView)
        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.error_thumbnail) // Use a default placeholder (optional)
                .into(holder.thumbnailView);

        }


        holder.itemView.setOnClickListener {
            onVideoClick(video)
        }
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.video_title)
        val thumbnailView: ImageView = itemView.findViewById(R.id.video_thumbnail)
        // Other view references (optional)
    }

    // Hàm lấy URL ảnh thu nhỏ YouTube từ liên kết (giả sử định dạng cụ thể)
    private fun getYouTubeThumbnailUrl(link: String): String? {
        val videoId = extractVideoIdFromLink(link) // Triển khai logic để trích xuất ID video từ URL YouTube
        if (videoId != null) {
            return "https://img.youtube.com/vi/$videoId/mqdefault.jpg" // Ảnh thu nhỏ chất lượng trung bình
        }
        return null
    }

    // Hàm trích xuất ID video từ liên kết YouTube (chi tiết triển khai bị bỏ qua)
    private fun extractVideoIdFromLink(link: String): String? {
        // Kiểm tra xem URL có bắt đầu bằng "https://www.youtube.com/embed/" hay không
        if (!link.startsWith("https://www.youtube.com/embed/")) {
            return null
        }

        // Lấy phần sau của URL sau "https://www.youtube.com/embed/"
        val videoId = link.substring("https://www.youtube.com/embed/".length)

        // Trả về ID video
        return videoId
    }

    fun updateVideos(newVideos: List<Video>) {
        videos.clear()
        videos.addAll(newVideos)
        notifyDataSetChanged()
    }
}