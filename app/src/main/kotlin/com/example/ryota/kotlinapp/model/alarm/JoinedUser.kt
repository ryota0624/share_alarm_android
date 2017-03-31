package com.example.ryota.kotlinapp.model.alarm

/**
 * Created by ryota on 2017/03/21.
 */



data class JoinedUser(val id: String, val name: String, val status: Status) {
    enum class Status {
        Joined,
        Sleeping,
        Wokeup
    }

}
