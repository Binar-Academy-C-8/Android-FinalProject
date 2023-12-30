package com.raveendra.finalproject_binar.presentation.popup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentOnboardingDialogBinding


class OnboardingDialogFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentOnboardingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_non_login_dialog, container, false)
    }
}