package com.sliide.usermanagement.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sliide.usermanagement.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

class RetrofitHandler {

    companion object {

        val INSTANCE: Retrofit by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.RETROFIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
            retrofit
        }

        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient
                .Builder()
                .addNetworkInterceptor(OkhttpInterceptor())
                .build()
        }
    }
}