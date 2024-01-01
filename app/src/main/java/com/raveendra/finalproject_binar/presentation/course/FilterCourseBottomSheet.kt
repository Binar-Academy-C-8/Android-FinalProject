package com.raveendra.finalproject_binar.presentation.course

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raveendra.finalproject_binar.databinding.FragmentFilterCourseBinding
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.presentation.course.adapter.CategoryFilterAdapter

class FilterCourseBottomSheet(
    private val context: Context,
    private val dataList : List<CategoryDomain>
) : BottomSheetDialogFragment() {
    var binding: FragmentFilterCourseBinding? = null

    private var selectedCategory : MutableList<Int?>? = mutableListOf()
    private var difficulty : String = ""

    private var handleOnDismiss: (() -> Unit)? = null

    lateinit var filterListenerClicked: ((category : List<Int?>?, difficulty : String) -> Unit)


    private val categoryFilterAdapter by lazy {

        CategoryFilterAdapter{
            if (it.isSelected == true){
                selectedCategory?.add(it.id)
            }else{
                selectedCategory?.remove(it.id)
            }
        }
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
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        binding?.apply {
            if(dataList.isNotEmpty()){
                categoryFilterAdapter.setData(dataList)
            }
            rgLevel.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton: RadioButton = group.findViewById(checkedId)
                difficulty = selectedRadioButton.text.toString()
            }
            rvCategoryFilter.adapter = categoryFilterAdapter
            btFilter.setOnClickListener {
                filterListenerClicked.invoke(selectedCategory,difficulty)
                dismiss()
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        handleOnDismiss?.invoke()
    }
}