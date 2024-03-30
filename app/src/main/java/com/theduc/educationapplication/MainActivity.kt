package com.theduc.educationapplication

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.theduc.educationapplication.databinding.ActivityMainBinding
import com.theduc.educationapplication.ui.calendar.CalendarFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_study, R.id.navigation_calendar,
                R.id.navigation_utilites, R.id.navigation_personal
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (intent.getBooleanExtra("openCalendarFragment", false)) {
            // Xoá HomeFragment khỏi stack để tránh lỗi không bấm được vào biểu tượng home
            navController.popBackStack(R.id.navigation_home, true)
            // Nếu có, mở CalendarFragment
            navController.navigate(R.id.navigation_calendar)
        }
    }

}