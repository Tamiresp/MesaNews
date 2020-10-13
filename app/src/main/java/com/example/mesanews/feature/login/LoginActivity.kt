package com.example.mesanews.feature.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mesanews.R
import com.example.mesanews.data.entity.UserLogin
import com.example.mesanews.feature.cadastro.CadastroActivity
import com.example.mesanews.feature.home.HomeActivity
import com.example.mesanews.util.Constants.TOKEN
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private val presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.view = this

        btn_register.setOnClickListener {
            goRegister()
        }

        button_enter.setOnClickListener {
            presenter.signIn(UserLogin(edit_login.text.toString(), edit_password.text.toString()))
        }
    }

    override fun goRegister() {
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }

    override fun goToHome(token: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(TOKEN, token)
        startActivity(intent)
    }

    override fun showProgress() {
        progressBarLogin.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarLogin.visibility = View.GONE
    }
}