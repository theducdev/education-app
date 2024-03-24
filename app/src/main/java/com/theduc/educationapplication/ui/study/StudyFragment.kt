package com.theduc.educationapplication.ui.study

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theduc.educationapplication.R
import com.theduc.educationapplication.databinding.FragmentPersonalBinding
import com.theduc.educationapplication.databinding.FragmentStudyBinding
import com.theduc.educationapplication.ui.study.ButtonAdapter

class StudyFragment : Fragment() {

//    private lateinit var personalViewModel: PersonalViewModel
    private lateinit var binding: FragmentStudyBinding

    companion object {
        fun newInstance() = StudyFragment()
    }

    private lateinit var viewModel: StudyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(layoutInflater)

        val buttonListView = binding.buttonLV
        val buttonNames = listOf("Lớp tín chỉ", "Lớp hành chính", "Kết quả học tập", "Tiến trình học tập", "Thời khoá biểu", "Điểm rèn luyện") // Danh sách tên các nút

        val adapter = ButtonAdapter(requireContext(), buttonNames)


        buttonListView.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}