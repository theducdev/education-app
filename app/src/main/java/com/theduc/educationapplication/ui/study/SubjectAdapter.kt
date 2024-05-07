package com.theduc.educationapplication.ui.study

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.Subject

class SubjectAdapter(private val context: Context, private val subjectList: List<Subject>) :
    RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_subject)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantity)
        val resultTextView: TextView = itemView.findViewById(R.id.result_subject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subject = subjectList[position]
        holder.nameTextView.text = subject.name
        holder.quantityTextView.text = subject.quantity.toString()
        holder.resultTextView.text = subject.result
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }
}
