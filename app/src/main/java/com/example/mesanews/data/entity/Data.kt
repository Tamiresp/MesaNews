package com.example.mesanews.data.entity

data class User (
    val name: String,
    val email: String,
    val password: String
)

data class UserLogin (
    val email: String,
    val password: String
)

data class Token(
    val token: String
)
