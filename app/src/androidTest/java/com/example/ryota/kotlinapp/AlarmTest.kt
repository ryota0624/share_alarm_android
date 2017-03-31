package com.example.ryota.kotlinapp

import android.support.test.filters.SmallTest
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.TestCase


/**
 * Created by ryota on 2017/03/30.
 */
@RunWith(JUnit4::class)
class AlarmTest: TestCase() {
    @SmallTest
    fun getId() {
        assertEquals(0, 9)
    }

}