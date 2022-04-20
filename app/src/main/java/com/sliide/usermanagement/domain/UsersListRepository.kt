package com.sliide.usermanagement.domain

import com.sliide.usermanagement.models.AddUserRequestBody
import com.sliide.usermanagement.models.UsersItem
import com.sliide.usermanagement.network.services.ApiHandler
import retrofit2.Response

class UsersListRepository {

    suspend fun getUsers(): Response<List<UsersItem>> {
        return ApiHandler.userApi.getUsersList()
    }
    suspend fun addNewUser(name: String, email: String, gender: String): Response<UsersItem> {
        return ApiHandler.userApi.addNewUser(AddUserRequestBody(name,gender,email,"active"))
    }
    suspend fun deleteUser(id:String): Response<Any> {
        return ApiHandler.userApi.deleteUser(id)
    }
}