package com.jaberkotlinproject.chatapp

data class User(var name: String?,
                var email: String?,
                var uid: String?) {
    constructor(): this(null, null, null)
}