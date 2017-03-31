package com.example.ryota.kotlinapp.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.test.mock.MockContext
import android.util.Log
import com.example.ryota.kotlinapp.gateway.AlarmGateway
import com.example.ryota.kotlinapp.gateway.UserGateway
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.user.User
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*
import java.util.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)

        verticalLayout {
            button("アラーム作成") {
                onClick { startAlarmCreate() }
            }
            button("アラーム一覧") {
                onClick { startAlarmList() }
            }
            button("招待コード入力") {
                onClick { startInviteCodeInput() }
            }
            button("login") {
                onClick { startLogin() }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (User.me == null) {
            startLogin()
        }
    }

    fun startLogin() {
        startActivity(Intent(application, LoginActivity::class.java))
    }

    fun startInviteCodeInput() {
        startActivity(Intent(application, InviteCodeActivity::class.java))
    }

    fun startAlarmList() {
        startActivity(Intent(application, AlarmListActivity::class.java))
    }

    fun startAlarmCreate() {
        startActivity(Intent(application, AlarmCreateActivity::class.java))
    }
}