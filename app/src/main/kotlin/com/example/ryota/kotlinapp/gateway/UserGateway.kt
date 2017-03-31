package com.example.ryota.kotlinapp.gateway

import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import java.io.InvalidObjectException
import java.security.InvalidParameterException

/**
 * Created by ryota on 2017/03/30.
 */
class UserGateway: UserRepository {
    override fun findOneById(id: String, onSuccess: (User) -> Unit, onError: (Error) -> Unit) {
        onError(Error("not impl"))
    }

    override fun findMe(id: String, password: String, onSuccess: (User) -> Unit, onError: (Error) -> Unit) {
        val request = FirebaseAuth.getInstance().signInWithEmailAndPassword(id, password)
        request.addOnSuccessListener {
            val username = it.user.email?.split("@")?.get(0) ?: throw InvalidParameterException()
            onSuccess(User(User.ID(username), User.Name(username), null))
        }

        request.addOnFailureListener {
            onError(Error(it.message))
        }
    }

    override fun store(user: User, onSuccess: (User) -> Unit, onError: (Error) -> Unit) {
        if (user.password is String) {
            val request = FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.name.value, user.password)
            request.addOnFailureListener {
                onError(Error(it.message))
            }

            request.addOnSuccessListener {
                onSuccess(User(id = User.ID(it.user.uid), name = user.name, password = null))
            }
        } else {
            onError(Error("don't have password"))
        }
    }
}