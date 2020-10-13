package com.example.mesanews.data.repository.cadastro

import com.example.mesanews.data.entity.User
import io.reactivex.Single

interface ICadastroRepository {
    //fun saveSp(token: String)

    fun signUp(user: User): Single<String>
}