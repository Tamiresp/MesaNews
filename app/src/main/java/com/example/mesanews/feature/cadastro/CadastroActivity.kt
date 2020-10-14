package com.example.mesanews.feature.cadastro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mesanews.R
import com.example.mesanews.data.entity.User
import com.example.mesanews.feature.home.HomeActivity
import com.example.mesanews.util.Constants.TOKEN
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity(), CadastroContract.View {
    private val sp: SharedPreferences by lazy {
        getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
    }

    private val presenter = CadastroPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        supportActionBar?.title = getString(R.string.cadastro)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        presenter.view = this

        enterRegister.setOnClickListener {
            if (verifyPassword()){
                presenter.signUp(User(name_register.text.toString(), email_register.text.toString(),
                    pass_register.text.toString()))
            }
        }

        cancelRegister.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun verifyPassword(): Boolean {
        return if(name_register.text.toString().isNotEmpty() && email_register.text.toString().isNotEmpty()
            && pass_register.text.toString().isNotEmpty() && pass_confirm.text.toString().isNotEmpty()){
            if (pass_register.text.toString() == pass_confirm.text.toString()) {
                true
            } else {
                Snackbar.make(findViewById(R.id.layout_register), R.string.dont_match, Snackbar.LENGTH_LONG).show()
                false
            }
        } else {
            showDialog(getString(R.string.fields))
            false
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun goToHome(token: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(TOKEN, token)
        startActivity(intent)
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
}