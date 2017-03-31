package com.example.ryota.kotlinapp.service.user

import com.example.ryota.kotlinapp.gateway.UserGateway
import com.example.ryota.kotlinapp.service.Service
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.repository.UserRepository

/**
 * Created by ryota on 2017/03/30.
 */

abstract class SignUpUser: Service<User, User> {
    abstract val userRepository: UserRepository
    override fun execute(input: User, onSuccess: (User) -> Unit, onError: (Error) -> Unit) {
        userRepository.store(input, {
            User.me = it
            onSuccess(it)
        }, onError)
    }

    companion object {
        fun instance(): SignUpUser {
            return object: SignUpUser() {
                override val userRepository: UserRepository
                    get() = UserGateway()
            }
        }
    }
}