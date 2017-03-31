package com.example.ryota.kotlinapp.model.user

/**
 * Created by ryota on 2017/03/21.
 */
data class User(val id: ID?, val name: Name, val password: String?) {
    data class ID(val value: String)
    data class Name(val value: String)
    companion object  {
        var me: User? = null
    }
}