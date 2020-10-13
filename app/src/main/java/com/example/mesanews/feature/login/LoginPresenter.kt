package com.example.mesanews.feature.login

import com.example.mesanews.data.entity.UserLogin
import com.example.mesanews.data.repository.login.LoginRepository
import com.google.android.play.core.splitcompat.d
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable

class LoginPresenter: LoginContract.Presenter {
    lateinit var view: LoginContract.View
    private val repository = LoginRepository()

    override fun signIn(userLogin: UserLogin) {
       repository.signIn(userLogin)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe{ d -> view.showProgress() }
            .doFinally{view.hideProgress() }
            .subscribe (
                {token -> view.goToHome(token)},
                {t: Throwable? ->  view.hideProgress()}
        )
    }
}