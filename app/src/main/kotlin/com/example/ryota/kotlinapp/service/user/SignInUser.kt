package com.example.ryota.kotlinapp.service.user

import com.example.ryota.kotlinapp.gateway.UserGateway
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.repository.UserRepository
import com.example.ryota.kotlinapp.service.Service

/**
 * Created by ryota on 2017/03/30.
 */

data class SingInArguments(val username: String, val password: String)
abstract class SignInUser: Service<SingInArguments, User> {
    abstract val userRepository: UserRepository
    override fun execute(input: SingInArguments, onSuccess: (User) -> Unit, onError: (Error) -> Unit) {
        userRepository.findMe(input.username, input.password, {
            User.me = it
            onSuccess(it)
        }, onError)
    }

    companion object {
        fun instance(): SignInUser {
            return object: SignInUser() {
                override val userRepository: UserRepository
                    get() = UserGateway()
            }
        }
    }
}