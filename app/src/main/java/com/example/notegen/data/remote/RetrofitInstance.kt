package com.example.notegen.data.remote

import com.example.notegen.data.network.AuthInterceptor
import com.example.notegen.data.remote.api.AuthApi
import com.example.notegen.data.remote.api.NoteApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.notegen.BuildConfig

object RetrofitInstance {


    // 🔓 Public (Login / Register)
    private val publicRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        publicRetrofit.create(AuthApi::class.java)
    }

    val noteApi: NoteApi by lazy {
        publicRetrofit.create(NoteApi::class.java)
    }



    // 🔐 Authenticated (Update Profile / Password)
    fun getAuthRetrofit(token: String): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
