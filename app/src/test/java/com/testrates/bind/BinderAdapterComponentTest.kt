package com.testrates.bind

import io.mockk.mockk
import org.junit.Test
import org.junit.Before

class BinderAdapterComponentTest {
    lateinit var binderAdapterComponent: BinderAdapterComponent

    @Before
    fun setUp(){
        mockk<BinderClass>()
        binderAdapterComponent = BinderAdapterComponent()
    }

    @Test
    fun getBinderClass() {
        binderAdapterComponent.binderClass
    }
}