package com.example.ryota.kotlinapp.Activities

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.Debug
import android.os.IBinder
import android.util.Log
import android.view.WindowManager
import android.widget.Toast

import com.example.ryota.kotlinapp.R
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.service.alarm.SubscribeAlarm
import org.jetbrains.anko.ctx
import java.io.Serializable
import java.util.*
import kotlin.concurrent.timer

class AlarmSubscribeActivity : Activity() {
    class Args(val alarmId: String, val userId: String, val startAlarm: Boolean) : Serializable

    lateinit var alarm: Alarm
    lateinit var mediaPlayer: MediaPlayer
    lateinit var alarmId: String
    lateinit var userId: String
    var startAlarm: Boolean = false
    lateinit var unSubscribeHandler: () -> Unit
    var alarmManager: AlarmManager? = null
    var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val args = intent.getSerializableExtra("args") as Args
        alarmId = args.alarmId
        userId = args.userId
        startAlarm = args.startAlarm
        mediaPlayer = MediaPlayer()
        setContentView(R.layout.activity_alarm_subscribe)
        Log.d("share_alarm", alarmId + userId)
        Toast.makeText(ctx, alarmId + userId, Toast.LENGTH_LONG).show()

    }

    fun startAlarmMusic() {
        mediaPlayer.setDataSource(alarm.musicUrl)
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.prepareAsync()
        mediaPlayer.isLooping = true
    }

    fun sample() {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.add(Calendar.SECOND, 10)
        cal.set(Calendar.MILLISECOND, 0)
        pendingIntent?.let {
        }
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager?.let {
            alarmManager!!.set(AlarmManager.RTC, cal.timeInMillis, pendingIntent);

        }
    }

    fun stopAlarmMusic() {
        mediaPlayer.stop()
    }

    fun startTimer() {
       Timer().schedule(object: TimerTask() {
           override fun run() {
               timerLoop()
           }
       } , 0, 1000 * 60)
    }

    fun timerLoop() {
        Log.d("share_alarm", "fjao")
        if(Date().time >= (alarm.time).time) {
            startAlarmMusic()
        }
    }

    override fun onStart() {
        super.onStart()
        unSubscribeHandler = SubscribeAlarm.instance.execute(SubscribeAlarm.Args(userId = userId, alarmId = alarmId),
                {
                    // 自分の状態がsleepingだったらserviceは起動させない
                    alarm = it

                    // 時間がすぎてこのページにきた場合カウントダウンはしない
                    if(Date().time < (alarm.time).time) {
                        startTimer()
                    }
                },
                {
                    Log.d("share_alarm", it.message)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        unSubscribeHandler()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
//
//class AlarmService : Service() {
//    override fun onBind(intent: Intent?): IBinder {
//        //
//        return Binder()
//    }
//
//    override fun bindService(service: Intent?, conn: ServiceConnection?, flags: Int): Boolean {
//        Log.d("share_alarm", "bindService")
//
//        return super.bindService(service, conn, flags)
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        task.run()
//    }
//
//    val task: Runnable = Runnable {
//        val alarmBroadcast = Intent()
//        alarmBroadcast.action = "AlarmAction"
//        alarmBroadcast.putExtra("alarmId", "hogheo")
//        alarmBroadcast.putExtra("userId", "hgojoa")
//        sendBroadcast(alarmBroadcast)
//        stopSelf()
//    }
//
//}
//
//class AlarmNotificationReceiver : BroadcastReceiver() {
//    override fun onReceive(ctx: Context?, intent: Intent?) {
//        Log.d("share_alarm", "AlarmNotificationReceiver")
//        val alarmActivity = Intent(ctx, AlarmSubscribeActivity::class.java)
//        alarmActivity.putExtra("args",
//                AlarmSubscribeActivity.Args(
//                        userId = intent!!.getStringExtra("userId"),
//                        alarmId = intent.getStringExtra("alarmId"),
//                        startAlarm = true))
//        alarmActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        ctx?.startActivity(alarmActivity)
//    }
//}
//
//        val intent = Intent(ctx, AlarmService::class.java).apply {
//            putExtra("alarmId", alarmId)
//            putExtra("userId", userId)
//        }

//        pendingIntent = PendingIntent.getService(ctx, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT)

//        sample()