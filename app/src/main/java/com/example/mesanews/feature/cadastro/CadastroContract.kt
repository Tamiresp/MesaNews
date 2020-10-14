package com.example.mesanews.feature.cadastro

import com.example.mesanews.data.entity.User

interface CadastroContract {
    interface View{
        fun verifyPassword(): Boolean

        fun onSuccessSignUp()

        fun onFailSignUp(msg: String)

        fun showProgress()

        fun hideProgress()

        fun goToHome(token: String)

        fun showDialog(msg: String)
    }

    interface Presenter{
        fun signUp(user: User)
    }
}