package com.example.ryota.kotlinapp.views.Login

import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.ryota.kotlinapp.Activities.LoginActivity
import com.example.ryota.kotlinapp.gateway.UserGateway
import com.example.ryota.kotlinapp.model.user.User
import com.example.ryota.kotlinapp.service.user.SignInUser
import com.example.ryota.kotlinapp.service.user.SingInArguments
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*

/**
 * Created by ryota on 2017/03/31.
 */

class LoginUi(var onReceiveUser: (User) -> Unit) : AnkoComponent<LoginActivity> {
    lateinit var errorText: TextView
    override fun createView(ui: AnkoContext<LoginActivity>): View = with(ui) {
        verticalLayout {
            val username = editText() {
                hint = "Name"
                maxLines = 1
                textSize = 24f
                setText("2010156ryota@example.com")

            }

            val password = editText() {
                hint = "Password"
                maxLines = 1
                textSize = 24f
                setText("2010156ryota")
                inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            button("signIn") {
                onClick {
                    signIn(username.text.toString(), password.text.toString())
                }
            }
            button("singUp") {

            }

            errorText = textView()
        }
    }

    fun signIn(username: String, password: String) {
        SignInUser.instance().execute(SingInArguments(username, password), {
            onReceiveUser(it)
        }, {
            errorText.text = it.message
        })
    }
}