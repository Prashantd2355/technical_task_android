package com.sliide.usermanagement.network.services

import com.sliide.usermanagement.models.AddUserRequestBody
import com.sliide.usermanagement.models.UsersItem
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("public/v2/users")
    suspend fun getUsersList(): Response<List<UsersItem>>

    @POST("public/v2/users")
    suspend fun addNewUser(@Body addUserRequestBody: AddUserRequestBody): Response<UsersItem>

    @DELETE("public/v2/users/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Any>

}