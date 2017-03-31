package com.example.ryota.kotlinapp.model.alarm

import com.example.ryota.kotlinapp.model.user.User
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import java.util.*

/**
 * Created by ryota on 2017/03/21.
 */


data class Alarm(val id: ID?, val time: Date, val userId: User.ID, val duration: Int,
                 val title: Title, val vibration: Vibration, val musicUrl: String, val joinedUsers: JoinedUsers) {
    data class ID(val value: String)
    data class Title(val value: String)
    sealed class Vibration(val value: Boolean) {
        object ON : Vibration(true)
        object OFF : Vibration(false)
        companion object {
            fun fromBool(bool: Boolean) = if (bool) ON else OFF
        }
    }

    data class JoinedUsers(val value: List<JoinedUser> = emptyList())

    companion object {
        val empty = Alarm(
                id = ID(""),
                duration = 0, userId = User.ID(""),
                vibration = Vibration.ON, joinedUsers = JoinedUsers(),
                time = Date(), title = Title(""),
                musicUrl = ""
        )
    }
}