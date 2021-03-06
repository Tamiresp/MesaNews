package com.example.mesanews.feature.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.mesanews.R
import com.example.mesanews.data.entity.UserLogin
import com.example.mesanews.feature.cadastro.CadastroActivity
import com.example.mesanews.feature.home.HomeActivity
import com.example.mesanews.util.Constants
import com.example.mesanews.util.Constants.TOKEN
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginContract.View {
    private val presenter = LoginPresenter()
    private lateinit var callbackManager: CallbackManager

    private val sp: SharedPreferences by lazy {
        getSharedPreferences(Constants.TOKEN_SAVE, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        callbackManager = CallbackManager.Factory.create()
        login_button_facebook.setReadPermissions("email")

        login_button_facebook.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val token = loginResult?.accessToken?.token
                    if (token != null) {
                        goToHome(token)
                    }
                }

                override fun onCancel() {

                }

                override fun onError(exception: FacebookException) {

                }
            })
        presenter.view = this

        btn_register.setOnClickListener {
            goRegister()
        }

        button_enter.setOnClickListener {
            if (verifyFields())
                presenter.signIn(
                    UserLogin(
                        edit_login.text.toString(),
                        edit_password.text.toString()
                    )
                )
            else
                showDialog(getString(R.string.fields))
        }

    }

    override fun goRegister() {
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }

    override fun goToHome(token: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(TOKEN, token)
        saveSp(token)
        startActivity(intent)
    }

    override fun showProgress() {
        progressBarLogin.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarLogin.visibility = View.GONE
    }

    override fun showDialog(msg: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(msg)
        builder.setPositiveButton("Ok"){ _, _ ->
            return@setPositiveButton
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun saveSp(token: String){
        sp.edit{
            putString(Constants.TOKEN_SAVE, token)
        }
    }

    override fun verifyFields(): Boolean{
        return edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}