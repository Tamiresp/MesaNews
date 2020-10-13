package com.example.mesanews.data.repository.login

import com.example.mesanews.data.entity.UserLogin
import io.reactivex.Single

interface ILoginRepository {
    fun signIn(
        user: UserLogin): Single<String>
}