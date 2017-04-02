package com.example.ryota.kotlinapp.service

/**
 * Created by ryota on 2017/03/21.
 */


interface Service<in I, out O> {
    fun execute(input: I, onSuccess: (O) -> Unit, onError: (Error) -> Unit)
}

interface SubscribeService<in I, out O> {
    fun execute(input: I, onSuccess: (O) -> Unit, onError: (Error) -> Unit): () -> Unit
}