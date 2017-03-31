package com.example.ryota.kotlinapp.views.AlarmList

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.ryota.kotlinapp.Activities.AlarmListActivity
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.service.alarm.ListMyAlarm
import org.jetbrains.anko.*

/**
 * Created by ryota on 2017/03/30.
 */

class AlarmListUi(var ctx: Context, var onClickAlarm: (Alarm) -> Unit) : AnkoComponent<AlarmListActivity> {
    lateinit var alarmListAdapter: AlarmListAdaptor
    lateinit var errorMessageView: TextView
    override fun createView(ui: AnkoContext<AlarmListActivity>): View = with(ui) {
        alarmListAdapter = AlarmListAdaptor(ctx, emptyList(), onClickAlarm)
        verticalLayout {
            listView {
                adapter = alarmListAdapter
            }
            errorMessageView = textView()
        }
    }

    fun fetchAlarms() {
        User.me?.id?.let {
            ListMyAlarm.instance().execute(it, {
                alarmListAdapter.updateAlarms(it)
            }, {
                errorMessageView.text = it.message
            })
        }
    }
}