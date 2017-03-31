package com.example.ryota.kotlinapp.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

import com.example.ryota.kotlinapp.R
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.user.User
import org.jetbrains.anko.onClick
import java.io.Serializable
import java.util.*

class AlarmDetailActivity : AppCompatActivity() {
    class Args(val id: String, val time: Date, val userId: String, val duration: Int,
               val title: String, val vibration: Boolean, val musicUrl: String): Serializable {
        fun toAlarm(): Alarm = Alarm(id = Alarm.ID(id), title = Alarm.Title(title), time = time,
                userId = User.ID(userId), vibration = Alarm.Vibration.fromBool(vibration),
                musicUrl = musicUrl, joinedUsers = Alarm.JoinedUsers(), duration = duration)

        companion object {
            fun fromAlarm(alarm: Alarm) = with(alarm) {
                Args(id = id!!.value, time = time, userId = userId.value, duration = duration,
                        title = title.value, vibration = vibration.value, musicUrl = musicUrl)
            }
        }
    }
    lateinit var alarm: Alarm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = intent.getSerializableExtra("alarm") as Args
        alarm = args.toAlarm()
        setContentView(R.layout.activity_alarm_detail)

        (findViewById(R.id.title) as TextView).apply {
            text = alarm.title.value
        }

        (findViewById(R.id.oyasumiButton) as Button).apply {
            onClick { onClickOyasumi() }
        }
    }

    fun onClickOyasumi() {
        val intent = Intent(this, AlarmSubscribeActivity::class.java).apply {
//            putExtra("alarm", AlarmSubscribeActivity.Args(alarm = alarm))
        }

        startActivity(intent)
    }
}

