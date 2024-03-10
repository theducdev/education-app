package com.theduc.educationapplication.ui.utilities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theduc.educationapplication.R

class UtilitiesFragment : Fragment() {

    companion object {
        fun newInstance() = UtilitiesFragment()
    }

    private lateinit var viewModel: UtilitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_utilities, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UtilitiesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}