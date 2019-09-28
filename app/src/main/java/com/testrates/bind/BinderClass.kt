package com.testrates.bind

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.testrates.R
import com.testrates.utils.getNumberFormat

class BinderClass {
    @BindingAdapter("bind:imageSrc")
    fun setCountryImage(imageView: ImageView, countryValue: Int) {
        imageView.setBackgroundResource(countryValue)
    }

    @BindingAdapter("bind:textFormat")
    fun setEditTextFormat(editText: EditText, editValue: Double) {
        val number = getNumberFormat()
        editText.setText(number.format(editValue))
    }

    @BindingAdapter("bind:textViewFormat")
    fun setTextViewFormat(text: TextView, editValue: Double) {
        val number = getNumberFormat()
        text.text = number.format(editValue)
    }

    @BindingAdapter("bind:textResource")
    fun setStringResource(textView: TextView, id: Int) {
        textView.text = textView.resources.getString(if (id == 0) R.string.app_name else id)
    }
}