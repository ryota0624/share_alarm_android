package com.example.ryota.kotlinapp.service.alarm

import com.example.ryota.kotlinapp.gateway.AlarmGateway
import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.repository.AlarmRepository
import com.example.ryota.kotlinapp.service.Service
import java.util.concurrent.Future

/**
 * Created by ryota on 2017/03/21.
 */
abstract class ListMyAlarm: Service<User.ID, List<Alarm>> {
    abstract val alarmGateway: AlarmGateway
    override fun execute(input: User.ID, onSuccess: (List<Alarm>) -> Unit, onError: (Error) -> Unit) {
        return alarmGateway.getListByUserId(input, onSuccess, onError)
    }
    companion object {
        fun instance(): ListMyAlarm {
            return object: ListMyAlarm() {
                override val alarmGateway: AlarmGateway
                    get() = AlarmGateway()
            }
        }
    }
}