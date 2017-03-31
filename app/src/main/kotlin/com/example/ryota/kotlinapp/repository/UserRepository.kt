package com.example.ryota.kotlinapp.repository

import com.example.ryota.kotlinapp.model.user.User

/**
 * Created by ryota on 2017/03/30.
 */
interface UserRepository {
    fun store(user: User, onSuccess: (User) -> Unit, onError: (Error) -> Unit)
    fun findOneById(id: String, onSuccess: (User) -> Unit, onError: (Error) -> Unit): Unit
    fun findMe(id: String, password: String, onSuccess: (User) -> Unit, onError: (Error) -> Unit): Unit
}