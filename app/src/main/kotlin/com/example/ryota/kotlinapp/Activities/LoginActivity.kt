package com.example.ryota.kotlinapp.Activities

import android.os.Bundle
import android.app.Activity
import android.util.Log
import com.example.ryota.kotlinapp.gateway.AlarmGateway
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.views.Login.LoginUi
import org.jetbrains.anko.setContentView
import java.util.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = LoginUi {
            finish()
        }
        ui.setContentView(this)
    }
}

