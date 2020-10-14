package com.example.mesanews.feature.login

import com.example.mesanews.data.entity.UserLogin

interface LoginContract {
    interface View{
        fun goRegister()

        fun goToHome(token: String)

        fun showProgress()

        fun hideProgress()

        fun showDialog(msg: String)

        fun verifyFields(): Boolean
    }

    interface Presenter{
        fun signIn(userLogin: UserLogin)
    }
}