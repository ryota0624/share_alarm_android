package com.example.ryota.kotlinapp.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.ryota.kotlinapp.R
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.service.alarm.ListMyAlarm
import com.example.ryota.kotlinapp.views.AlarmList.AlarmListAdaptor
import com.example.ryota.kotlinapp.views.AlarmList.AlarmListUi
import org.jetbrains.anko.*

class AlarmListActivity : Activity() {
    val ui = AlarmListUi(ctx, this::onClickAlarm)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)
    }

    override fun onStart() {
        super.onStart()
        ui.fetchAlarms()
    }

    fun onClickAlarm(alarm: Alarm) {
        val intent = Intent(this, AlarmDetailActivity::class.java).apply {
            putExtra("alarm",
                    AlarmDetailActivity.Args.fromAlarm(alarm))
        }

        startActivity(intent)
    }
}

