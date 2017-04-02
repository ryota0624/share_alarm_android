package com.example.ryota.kotlinapp.repository

import com.example.ryota.kotlinapp.model.alarm.Alarm
import com.example.ryota.kotlinapp.model.user.User
import java.util.concurrent.Future

/**
 * Created by ryota on 2017/03/21.
 */
interface AlarmRepository {
    fun getOneById(id: Alarm.ID, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): Unit
    fun store(alarm: Alarm, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): Unit
    fun getListByUserId(Id: User.ID, onSuccess: (List<Alarm>) -> Unit, onError: (Error) -> Unit): Unit
    fun subscribeOne(userId: User.ID, id: Alarm.ID, onSuccess: (Alarm) -> Unit, onError: (Error) -> Unit): () -> Unit
}