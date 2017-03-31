package com.example.ryota.kotlinapp.gateway

import android.test.mock.MockContext
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by ryota on 2017/03/30.
 */
class UserGatewayTest {
    @Test
    fun findMe() {
        FirebaseApp.initializeApp(MockContext())
        val gw = UserGateway()
        FirebaseAuth.getInstance()
        gw.findMe("2010156ryota@example.com", "2010156ryta", {
            println(it)
            kotlin.test.assertTrue(true)
        }, {
            kotlin.test.assertTrue(false)
        })
    }
}