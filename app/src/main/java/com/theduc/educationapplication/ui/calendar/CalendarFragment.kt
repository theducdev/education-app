package com.theduc.educationapplication.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.theduc.educationapplication.R


class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var listView: ListView
    private lateinit var classes: MutableList<String>

    companion object {
        fun newInstance() = CalendarFragment()
    }

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendar)
        listView = view.findViewById(R.id.list_classes)

        // Khởi tạo danh sách lịch rỗng
        classes = mutableListOf()
        val classDataRef =  initFirebase()



        // Khởi tạo Adapter cho ListView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, classes)
        listView.adapter = adapter

        // Thiết lập sự kiện khi chọn ngày trên CalendarView
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Xóa dữ liệu cũ của ListView
            classes.clear()

            // Format the selected date
            val selectedDate = String.format("%d%02d%d", year, month + 1, dayOfMonth)


            val dateNodeRef = classDataRef.child(selectedDate)

            // Read data from Firebase using a ValueEventListener
            dateNodeRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Check if data exists for the selected date

                    if (snapshot.exists()) {
                        // Loop through child nodes (assuming each class is a child node)
                        for (childSnapshot in snapshot.children) {
                            val classData = childSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {}) // Sử dụng GenericTypeIndicator để định dạng kiểu dữ liệu
                            val className = classData?.get("name") as String
                            val classTime = classData["time"] as String
                            val classInfo = "Môn: $className\nThời gian: $classTime"
                            classes.add(classInfo)
                        }
                        // Notify adapter about data change
                        adapter.notifyDataSetChanged()
                    } else {
                        // Handle case where no data exists for the selected date
                        classes.add("Không có lịch học")
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(),"Error occurred", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initFirebase(): DatabaseReference {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        // Get reference to the specific node in your database containing class data
        val classDataRef = firebaseDatabase.getReference("classes")
        return classDataRef
    }

}