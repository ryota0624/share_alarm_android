package com.example.ryota.kotlinapp.service.alarm

import com.example.ryota.kotlinapp.gateway.AlarmGateway
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.service.SubscribeService

/**
 * Created by ryota on 2017/03/21.
 */
//
abstract class SubscribeAlarm : SubscribeService<SubscribeAlarm.Args, Alarm> {
    data class Args(val userId: String, val alarmId: String)
    abstract val alarmGateway: AlarmGateway

    override fun execute(input: Args, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): () -> Unit =
            alarmGateway.subscribeOne(User.ID(input.userId), Alarm.ID(input.alarmId), {
                onSuccess(it)
            }, {
                onError(it)
            })

    companion object {
        val instance: SubscribeAlarm = object: SubscribeAlarm() {
            override val alarmGateway: AlarmGateway = AlarmGateway()
        }
    }
}