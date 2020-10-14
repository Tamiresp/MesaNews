package com.example.mesanews.feature.cadastro

import com.example.mesanews.data.entity.User
import com.example.mesanews.data.repository.cadastro.CadastroRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CadastroPresenter: CadastroContract.Presenter {
    lateinit var view: CadastroContract.View
    private val repository = CadastroRepository()

    override fun signUp(user: User) {
        repository.signUp(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe{ d -> view.showProgress() }
            .doFinally{view.hideProgress() }
            .subscribe (
                {token -> view.goToHome(token)},
                {t: Throwable? ->
                    view.hideProgress()
                    if (t != null) {
                        view.showDialog(t.message.toString())
                    }
                }
            )
    }
}