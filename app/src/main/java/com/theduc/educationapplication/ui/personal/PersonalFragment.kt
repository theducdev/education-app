package com.theduc.educationapplication.ui.personal

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theduc.educationapplication.databinding.FragmentPersonalBinding
import com.theduc.educationapplication.ui.login.LoginActivity

class PersonalFragment : Fragment() {

    private lateinit var personalViewModel: PersonalViewModel
    private lateinit var binding: FragmentPersonalBinding

    companion object {
        fun newInstance() = PersonalFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPersonalBinding.inflate(layoutInflater)

        val buttonListView = binding.buttonLV
        val buttonNames = listOf("Thông tin cá nhân", "Đổi mật khẩu", "Đăng xuất") // Danh sách tên các nút

        val adapter = ButtonAdapter(requireContext(), buttonNames)


        buttonListView.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        personalViewModel = ViewModelProvider(this).get(PersonalViewModel::class.java)
        // TODO: Use the ViewModel



    }
    fun logout() {
        personalViewModel.logout()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}