package com.example.mesanews.data.repository.login

import android.util.Log
import com.example.mesanews.data.entity.Token
import com.example.mesanews.data.entity.UserLogin
import com.example.mesanews.service.Api
import com.example.mesanews.service.RestApi
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginRepository: ILoginRepository {
    override fun signIn(user: UserLogin): Single<String>{
        val result = Api.getInstance().create(RestApi::class.java).loginUser(user)
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