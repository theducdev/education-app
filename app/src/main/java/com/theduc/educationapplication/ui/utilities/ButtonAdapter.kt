package com.theduc.educationapplication.ui.utilities

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
import com.theduc.educationapplication.ui.personal.ChangePasswordActivity
import com.theduc.educationapplication.ui.study.LibraryActivity

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
                    val newsIntent = Intent(context, NewsActivity::class.java)
                    context.startActivity(newsIntent)
                    (context as Activity).finish()
                }


                2 -> {
                    val feedBackIntent = Intent(context, FeedbackActivity::class.java)
                    context.startActivity(feedBackIntent)
                    (context as Activity).finish()

                }

                3 -> {
                    val guideIntent = Intent(context, GuideActivity::class.java)
                    context.startActivity(guideIntent)
                    (context as Activity).finish()

                }

                4 -> {
                    val introduceIntent = Intent(context, IntroduceActivity::class.java)
                    context.startActivity(introduceIntent)
                    (context as Activity).finish()

                }

            }

        }

        return view!!
    }

}
