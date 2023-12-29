package com.raveendra.finalproject_binar.presentation.popup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentNonLoginDialogBinding
import com.raveendra.finalproject_binar.presentation.auth.login.LoginActivity


class NonLoginDialogFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentNonLoginDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNonLoginDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeNonLoginDialog()
        navigateToLogin()
    }

    private fun closeNonLoginDialog(){
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun navigateToLogin(){
        binding.btnMasuk.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}