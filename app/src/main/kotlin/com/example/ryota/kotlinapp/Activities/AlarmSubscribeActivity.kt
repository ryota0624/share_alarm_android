package com.example.ryota.kotlinapp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.ryota.kotlinapp.R
import com.example.ryota.kotlinapp.model.alarm.Alarm
import java.io.Serializable

class AlarmSubscribeActivity : AppCompatActivity() {
    class Args(val alarm: Alarm): Serializable
    lateinit var alarm: Alarm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = intent.getSerializableExtra("alarm") as Args
        alarm = args.alarm
        setContentView(R.layout.activity_alarm_subscribe)
    }
}

