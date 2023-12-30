package com.raveendra.finalproject_binar.presentation.popup

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentFilterCourseBinding

class FilterCourseBottomSheet(
    private val context: Context,
) : BottomSheetDialogFragment() {
    var binding: FragmentFilterCourseBinding? = null

    private var handleOnDismiss: (() -> Unit)? = null
    lateinit var listenerClicked: ((category : List<Int?>?, difficulty : String) -> Unit)

    private val onClickListenerInsuredCard = { category : List<Int?>?, difficulty : String ->
        listenerClicked.invoke(category, difficulty)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterCourseBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        handleOnDismiss?.invoke()
    }
}