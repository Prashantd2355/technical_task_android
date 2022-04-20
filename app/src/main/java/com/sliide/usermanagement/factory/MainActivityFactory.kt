package com.sliide.usermanagement.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sliide.usermanagement.domain.UsersListRepository
import com.sliide.usermanagement.viewmodel.UsersListViewModel

class MainActivityFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val usersListRepository = UsersListRepository()
        return UsersListViewModel(usersListRepository) as T
    }
}