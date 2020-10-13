package com.example.mesanews.data.repository.cadastro

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.mesanews.data.entity.Token
import com.example.mesanews.data.entity.User
import com.example.mesanews.service.Api
import com.example.mesanews.service.RestApi
import com.example.mesanews.util.Constants.TOKEN
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CadastroRepository(): ICadastroRepository {
//    private val sp: SharedPreferences by lazy {
//        context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
//    }

//    override fun saveSp(token: String) = sp.edit{
//        putString(TOKEN, token)
//    }

    override fun signUp(user: User): Single<String> {
        val result = Api.getInstance().create(RestApi::class.java).addUser(user)
        var token: String? = ""

        return Single.create { emitter ->
            result.enqueue(object : Callback<Token> {

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    emitter.onError(t)
                    Log.d("ERROR", t.message)
                }

                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful){
                        token = response.body()?.token
                        token?.let { emitter.onSuccess(it) }

                        Log.d("OK", token)
                    } else {
                        emitter.onError(Exception("Erro no login"))
                        Log.d("ERROR", response.message())
                    }
                }
            })
        }
    }
}