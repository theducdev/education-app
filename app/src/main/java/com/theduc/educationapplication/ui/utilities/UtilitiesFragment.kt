package com.theduc.educationapplication.ui.utilities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theduc.educationapplication.R
import com.theduc.educationapplication.databinding.FragmentPersonalBinding
import com.theduc.educationapplication.databinding.FragmentUtilitiesBinding

class UtilitiesFragment : Fragment() {

    private lateinit var binding: FragmentUtilitiesBinding

    companion object {
        fun newInstance() = UtilitiesFragment()
    }

    private lateinit var viewModel: UtilitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUtilitiesBinding.inflate(layoutInflater)

        val buttonListView = binding.buttonLV

        val buttonNames = listOf("Tin tức", "Phản hồi", "Văn bản hướng dẫn", "Giới thiệu") // Danh sách tên các nút


        val adapter = ButtonAdapter(requireContext(), buttonNames)


        buttonListView.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UtilitiesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}