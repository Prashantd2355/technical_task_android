package com.sliide.usermanagement.network.services

import com.sliide.usermanagement.network.RetrofitHandler

class ApiHandler {

    companion object {

        @JvmStatic
        val userApi: UserService by lazy {
            createRetrofitService<UserService>()
        }

        private inline fun <reified T> createRetrofitService() =
            RetrofitHandler.INSTANCE.create(T::class.java)
    }
}