package com.theduc.educationapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.theduc.educationapplication.R
import com.theduc.educationapplication.data.model.Class
import com.theduc.educationapplication.databinding.FragmentHomeBinding
import com.theduc.educationapplication.databinding.FragmentUtilitiesBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var CalendarRV: RecyclerView
    private var adapter: CalendarAdapter? = null // Adapter for the RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        CalendarRV = binding.recyclerView
        // Thiết lập LayoutManager cho RecyclerView
        CalendarRV.layoutManager = LinearLayoutManager(requireContext())

        // Prepare data source (replace with your actual data retrieval logic)

        getScheduleData { scheduleData ->
            // Xử lý dữ liệu trả về ở đây, ví dụ:
            adapter = CalendarAdapter(scheduleData)
            CalendarRV.setAdapter(adapter)
        }

        val buttonListView = binding.buttonLV

        val buttonNames = listOf("Lớp tín chỉ", "Kết quả học tập", "Thư viện học tập") // Danh sách tên các nút

        val adapter = ButtonAdapter(requireContext(), buttonNames)
        buttonListView.adapter = adapter


        return binding.root
    }

    // Implement a method to retrieve your schedule data (replace with your logic)
    private fun getScheduleData(callback: (MutableList<Map<String, MutableList<Class>>>) -> Unit) {
        val classes: MutableList<Map<String, MutableList<Class>>> = mutableListOf()
        val classDataRef = initFirebase()
        val calendar: Calendar = Calendar.getInstance()
        var countDownLatch = 5 // Số lượng lời gọi bất đồng bộ cần chờ

        for (i in 0 until 5) {
            calendar.add(Calendar.DAY_OF_MONTH, i)
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH) + 1
            val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val selectedDateForMap = String.format("%02d-%02d-%d", dayOfMonth, month, year)
            val selectedDate = String.format("%d%02d%d", year, month, dayOfMonth)

            val dateNodeRef = classDataRef.child(selectedDate)

            dateNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dailyClasses: MutableList<Class> = mutableListOf()
                    if (snapshot.exists()) {
                        for (childSnapshot in snapshot.children) {
                            val classData = childSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {})
                            val className = classData?.get("name") as String
                            val classTime = classData["time"] as String

                            dailyClasses.add(Class(className, classTime))
                        }
                    } else {
                        dailyClasses.add(Class("Không có lịch học", ""))
                    }

                    val calendarDate: MutableMap<String, MutableList<Class>> = mutableMapOf()
                    calendarDate[selectedDateForMap] = dailyClasses
                    classes.add(calendarDate)

                    countDownLatch--
                    if (countDownLatch == 0) {
                        // Tất cả các lời gọi bất đồng bộ đã hoàn thành, sắp xếp classes theo ngày và gọi callback với dữ liệu
                        val sortedClasses = classes.sortedBy { it.keys.first() }
                        callback(sortedClasses.toMutableList())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
                    countDownLatch-- // Giảm biến đếm trong trường hợp có lỗi xảy ra
                    if (countDownLatch == 0) {
                        // Nếu tất cả các lời gọi bất đồng bộ đã hoàn thành (hoặc có lỗi), gọi callback
                        callback(classes)
                    }
                }
            })

            calendar.add(Calendar.DAY_OF_MONTH, -i)
        }
    }


    private fun initFirebase(): DatabaseReference {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        // Get reference to the specific node in your database containing class data
        val classDataRef = firebaseDatabase.getReference("classes")
        return classDataRef
    }

}

class CalendarAdapter(private val calendarData: List<Map<String, MutableList<Class>>>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendarItem = calendarData[position]

        // Lấy thông tin từ dữ liệu lịch học
        val date = calendarItem.keys.first() // Lấy ngày
        val classes = calendarItem[date] // Lấy danh sách lớp học

        // Tạo một chuỗi để hiển thị thông tin về lịch học
        val stringBuilder = StringBuilder()
        classes?.forEach { classItem ->
            if (!classItem.time.equals("")) {
                stringBuilder.append("${classItem.name} - ${classItem.time}\n")
            } else {
                stringBuilder.append("${classItem.name}\n")
            }
        }


        // Hiển thị thông tin lên card view
        holder.dateTextView.text = date
        holder.classesTextView.text = stringBuilder.toString()
    }

    override fun getItemCount(): Int {
        return calendarData.size
    }

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.text_date)
        val classesTextView: TextView = itemView.findViewById(R.id.text_classes)
    }
}


