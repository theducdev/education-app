package com.theduc.educationapplication.ui.study

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.Student

class ClassMemberAdapter(private val context: Context, private val membersList: List<Student>) :
    RecyclerView.Adapter<ClassMemberAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sttTextView: TextView = itemView.findViewById(R.id.tv_stt)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = membersList[position]
        holder.sttTextView.text = (position + 1).toString()
        holder.nameTextView.text = student.name
    }

    override fun getItemCount(): Int {
        return membersList.size
    }
}
