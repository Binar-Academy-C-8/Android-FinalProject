package com.raveendra.finalproject_binar.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.google.android.material.textfield.TextInputLayout
import com.raveendra.finalproject_binar.R


class LabelTextFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val labelView: TextView
    private val layoutEditText: LinearLayoutCompat
    private val iconView: ImageView
    private val inputLayout: TextInputLayout
    private val dataView: EditText
    private val errorView: TextView
    private val labelCountryView: TextView

    // view properties
    @StyleRes
    private var labelViewStyle: Int = 0
    private var labelViewText: String? = null
    private var labelViewColor: Int = 0
    private var dataViewText: String? = null

    @StyleRes
    private var dataViewStyle: Int = 0
    private var dataViewColor: Int = 0
    private var dataViewHintText: String? = null
    private var dataViewHintTextColor: Int = 0
    private var tagText: String? = null
    private var iconViewDrawable: Drawable? = null
    private var errorViewText: String? = null
    private var isViewEnabled: Boolean = true
    private var backgroundOnFocus: Drawable? = null
    private var isError: Boolean = false
    private var customInputType: String? = null
    private var inputType: Int = InputType.TYPE_TEXT_VARIATION_NORMAL
    private var maxLines: Int = 0
    private var lines: Int = 0
    private var drawableStart: Drawable? = null
    private var maxLength: Int = 0
    private var isPasswordToggle: Boolean = false

    private var onTextChangeListener: TextWatcher? = null
    private var onIconClickListener: (() -> Unit)? = null

    private var customTypeText = ""
    private var customTypeTextArea = ""
    private var customTypeTextPhone = ""

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.label_text_field_view, this)

        customTypeText = context.resources.getString(R.string.input_type_text)
        customTypeTextArea = context.resources.getString(R.string.input_type_text_area)
        customTypeTextPhone = context.resources.getString(R.string.input_type_text_phone)

        labelView = view.findViewById(R.id.tv_custom_view_label)
        layoutEditText = view.findViewById(R.id.layout_custom_text_view)
        iconView = view.findViewById(R.id.iv_custom_text_view_icon)
        inputLayout = view.findViewById(R.id.til_custom_text_view)
        dataView = view.findViewById(R.id.et_custom_text_view)
        errorView = view.findViewById(R.id.tv_custom_text_error)
        labelCountryView = view.findViewById(R.id.labelCountry)

        setAttributes(attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun setAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.LabelEditTextView).run {
            initAttributes(this)
            recycle()
        }
    }

    private fun initAttributes(typedArray: TypedArray) {
        labelViewStyle = typedArray.getInt(R.styleable.LabelEditTextView_customLabelTextStyle, 0)
        labelViewColor = typedArray.getColor(R.styleable.LabelEditTextView_customLabelTextColor, 0)
        labelViewText = typedArray.getString(R.styleable.LabelEditTextView_customLabelText)
        dataViewStyle = typedArray.getInt(R.styleable.LabelEditTextView_customTextStyle, 0)
        dataViewColor = typedArray.getColor(R.styleable.LabelEditTextView_customTextColor, 0)
        dataViewText = typedArray.getString(R.styleable.LabelEditTextView_customText)
        dataViewHintText = typedArray.getString(R.styleable.LabelEditTextView_android_hint)
        dataViewHintTextColor = typedArray.getColor(R.styleable.LabelEditTextView_android_textColorHint, 0)
        tagText = typedArray.getString(R.styleable.LabelEditTextView_customTag)
        iconViewDrawable = typedArray.getDrawable(R.styleable.LabelEditTextView_customTextIcon)
        errorViewText = typedArray.getString(R.styleable.LabelEditTextView_customErrorText)
        isError = typedArray.getBoolean(R.styleable.LabelEditTextView_customErrorEnabled, false)
        customInputType = typedArray.getString(R.styleable.LabelEditTextView_customInputType)
        isViewEnabled = typedArray.getBoolean(R.styleable.LabelEditTextView_android_enabled, true)
        backgroundOnFocus = typedArray.getDrawable(R.styleable.LabelEditTextView_customBackgroundOnFocus)
        inputType = typedArray.getInt(R.styleable.LabelEditTextView_android_inputType, 1)
        maxLines = typedArray.getInt(R.styleable.LabelEditTextView_android_maxLines, 0)
        lines = typedArray.getInt(R.styleable.LabelEditTextView_android_lines, 0)
        drawableStart = typedArray.getDrawable(R.styleable.LabelEditTextView_android_drawableStart)
        maxLength = typedArray.getInt(R.styleable.LabelEditTextView_android_maxLength, 0)
        isPasswordToggle = typedArray.getBoolean(R.styleable.LabelEditTextView_passwordToggleEnabled, false)

        setViewAttributes()
    }

    private fun setViewAttributes() {

        labelView.isVisible = !labelViewText.isNullOrEmpty()
        labelView.apply {
            text = labelViewText
            if (labelViewStyle.isNotZero())
                TextViewCompat.setTextAppearance(this, labelViewStyle)
            if (labelViewColor.isNotZero()) {
                setTextColor(labelViewColor)
            }
        }
        dataView.apply {
            setText(dataViewText)
            if (dataViewStyle.isNotZero())
                TextViewCompat.setTextAppearance(this, dataViewStyle)
            if (dataViewColor.isNotZero()) {
                setTextColor(dataViewColor)
            }

            inputType = this@LabelTextFieldView.inputType
            if(customInputType == customTypeTextArea){
                layoutParams.height = context.resources.getDimensionPixelSize(R.dimen.dimen_96_dp)
            }
            if (this@LabelTextFieldView.maxLines.isNotZero()) maxLines =
                this@LabelTextFieldView.maxLines
            if (lines.isNotZero()) setLines(lines)
            hint = dataViewHintText
            setHintTextColor(dataViewHintTextColor)
        }
        if (backgroundOnFocus != null) {
            setOnFocusEdittext()
        }
        if (iconViewDrawable != null) {
            iconView.isVisible = true
            iconView.setImageDrawable(iconViewDrawable)
        } else {
            iconView.isVisible = false
        }

        drawableStart?.let { setIconStart(it) }

        if (errorViewText.isNullOrEmpty()) {
            errorView.isVisible = false
        } else {
            errorView.isVisible = true
            errorView.text = errorViewText
        }
        if (isPasswordToggle) inputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

        labelCountryView.isVisible = isTypeTextPhone()

        if (isViewEnabled) setView(CustomTextViewType.ENABLED) else setView(CustomTextViewType.DISABLED)

        if(isError){
            setError(isError, errorViewText)
        }

        if (maxLength.isNotZero()) {
            setMaxLength(maxLength)
        }
    }

    private fun setOnFocusEdittext() {
        dataView.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                layoutEditText.background = backgroundOnFocus
            } else {
                layoutEditText.background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_border_default)
            }
        }
    }

    private fun isTypeTextPhone(): Boolean {
        return customInputType == customTypeTextPhone
    }

    private fun Int.isNotZero(): Boolean {
        return this != 0
    }

    private fun setView(viewType: CustomTextViewType) {
        when (viewType) {
            CustomTextViewType.ENABLED -> {
                dataView.isEnabled = true
                layoutEditText.setBackgroundResource(R.drawable.bg_rounded_border_default)
            }
            CustomTextViewType.DISABLED -> {
                dataView.isEnabled = false
                layoutEditText.setBackgroundResource(R.drawable.bg_rounded_border_default)
            }
            CustomTextViewType.ERROR -> {
                dataView.isEnabled = true
                layoutEditText.setBackgroundResource(R.drawable.bg_rounded_border_error)
            }
        }

        setColorType(viewType)
    }

    private fun setColorType(viewType: CustomTextViewType){

        val colorDefault = ContextCompat.getColor(context, R.color.black)
        val colorHint = ContextCompat.getColor(context, R.color.neutral_03)
        val colorDisable = ContextCompat.getColor(context, R.color.black)
        val colorError = ContextCompat.getColor(context, R.color.alert_warning)
        val colorErrorText = ContextCompat.getColor(context, R.color.alert_warning)
        when (viewType) {
            CustomTextViewType.ENABLED -> {
                labelView.setTextColor(colorDefault)
                errorView.setTextColor(colorDefault)
                dataView.setTextColor(colorDefault)
                dataView.setHintTextColor(colorHint)
                labelCountryView.setHintTextColor(colorDefault)
            }
            CustomTextViewType.DISABLED -> {
                labelView.setTextColor(colorDisable)
                errorView.setTextColor(colorDisable)
                dataView.setTextColor(colorDisable)
                dataView.setHintTextColor(colorHint)
                labelCountryView.setHintTextColor(colorDisable)
            }
            CustomTextViewType.ERROR -> {
                labelView.setTextColor(colorError)
                errorView.setTextColor(colorError)
                dataView.setTextColor(colorErrorText)
                dataView.setHintTextColor(colorHint)
                labelCountryView.setHintTextColor(colorErrorText)
            }
            else -> {
                labelView.setTextColor(colorDefault)
                errorView.setTextColor(colorDefault)
                dataView.setTextColor(colorDefault)
                dataView.setHintTextColor(colorHint)
                labelCountryView.setHintTextColor(colorDefault)
            }
        }
    }

    fun setError(isError: Boolean, message: String? = null) {
        val viewType = if (isError) CustomTextViewType.ERROR else CustomTextViewType.ENABLED
        setView(viewType)
        setErrorView(isError, message)
    }

    private fun setIconStart(drawable: Drawable){
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        // where params are (left,top,right,bottom)
        dataView.setCompoundDrawables(drawable, null, null, null)
    }
    fun setIconEnd(drawable: Drawable){
        if (drawable != null) {
            iconView.isVisible = true
            iconView.setImageDrawable(drawable)
        } else {
            iconView.isVisible = false
        }
    }

    fun setMaxLength(length: Int) {
        dataView.filters = arrayOf(InputFilter.LengthFilter(length))
    }

    private fun setErrorView(isError: Boolean, message: String?) {
        errorView.isVisible = isError
        errorView.text = message
    }

    fun setOnTextChangedListener(textWatcher: TextWatcher?) {
        onTextChangeListener = textWatcher
        dataView.addTextChangedListener(onTextChangeListener)
    }

    fun removeOnTextChangedListener() {
        dataView.removeTextChangedListener(onTextChangeListener)
        onTextChangeListener = null
    }

    fun setIconEndOnClickListener(listener: (() -> Unit)) {
        onIconClickListener = listener
        iconView.setOnClickListener { onIconClickListener?.invoke() }
    }

    fun setText(textValue: String) {
        dataView.setText(textValue)
    }

    fun getText(): String? {
        return dataView.text?.toString()
    }
}