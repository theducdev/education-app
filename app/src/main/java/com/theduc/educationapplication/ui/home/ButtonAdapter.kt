package com.theduc.educationapplication.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import com.theduc.educationapplication.R
import com.theduc.educationapplication.ui.login.LoginActivity
import com.theduc.educationapplication.ui.study.ClassMemberActivity
import com.theduc.educationapplication.ui.study.LibraryActivity
import com.theduc.educationapplication.ui.study.SubjectMemberActivity
import com.theduc.educationapplication.ui.study.SubjectResultActivity
import com.theduc.educationapplication.ui.utilities.FeedbackActivity
import com.theduc.educationapplication.ui.utilities.GuideActivity
import com.theduc.educationapplication.ui.utilities.IntroduceActivity
import com.theduc.educationapplication.ui.utilities.NewsActivity

class ButtonAdapter(context: Context, private val buttonNames: List<String>) :
    ArrayAdapter<String>(context, R.layout.button_item, buttonNames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.button_item, parent, false)
        }

        val buttonItem = view?.findViewById<Button>(R.id.buttonItem)
        val buttonName = buttonNames[position]
        buttonItem?.text = buttonName

        // Xử lý sự kiện khi người dùng nhấn vào nút
        buttonItem?.setOnClickListener {
            // Xử lý logic khi người dùng nhấn vào nút ở vị trí position
            when(position) {
                0 -> {
                    val subjectMemberIntent = Intent(context, SubjectMemberActivity::class.java)
                    context.startActivity(subjectMemberIntent)
                    (context as Activity).finish()
                }

                1 -> {
                    val studyResultIntent = Intent(context, SubjectResultActivity::class.java)
                    context.startActivity(studyResultIntent)
                    (context as Activity).finish()
                }

                2 -> {
                    val libraryIntent = Intent(context, LibraryActivity::class.java)
                    context.startActivity(libraryIntent)
                    (context as Activity).finish()
                }
            }
        }

        return view!!
    }

}
