package com.testrates.bind

import android.content.Context
import android.content.res.Resources
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.testrates.R
import com.testrates.utils.getNumberFormat
import io.mockk.*
import org.junit.Test

class BinderClassTest {

    private val binderClass = BinderClass()
    private val numberFormat = getNumberFormat()

    @Test
    fun setCountryImage() {
    val imageView = mockk<ImageView>()
        val imageDrawable = R.drawable.flag_aud
        every { imageView.setBackgroundResource(imageDrawable) } just runs
        binderClass.setCountryImage(imageView,imageDrawable)
        verify { imageView.setBackgroundResource(imageDrawable) }
    }

    @Test
    fun setEditTextFormat() {
        val editText = mockk<EditText>()
        val editValue = 1.0478
        every { editText.setText(numberFormat.format(editValue)) } just runs
        binderClass.setEditTextFormat(editText,editValue)
        verify { editText.setText(numberFormat.format(editValue)) }
    }

    @Test
    fun setTextViewFormat() {
        val textView = mockk<TextView>()
        val textValue = 1.283
        every { textView.text = numberFormat.format(textValue) } just runs
        binderClass.setTextViewFormat(textView,textValue)
        verify { textView.text = numberFormat.format(textValue) }
    }

    @Test
    fun setStringResource() {
        val textView= mockk<TextView>()
        val textResource = R.string.long_aud
        val context = mockk<Context>()
        val resources  = mockk<Resources>()
        every { textView.context} returns context
        every { context.resources } returns resources
        every { textView.text = resources.getString(textResource) } just runs
        //binderClass.setStringResource(textView,textResource)
        //verify { textView.setText("Australian dollar") }
    }
}