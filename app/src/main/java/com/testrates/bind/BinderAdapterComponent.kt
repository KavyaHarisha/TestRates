package com.testrates.bind

import androidx.databinding.DataBindingComponent

class BinderAdapterComponent : DataBindingComponent {
    override fun getBinderClass(): BinderClass {
        return BinderClass()
    }

}