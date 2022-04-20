package com.sliide.usermanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sliide.usermanagement.Constants
import com.sliide.usermanagement.Coroutines
import com.sliide.usermanagement.domain.UsersListRepository
import com.sliide.usermanagement.models.UsersItem
import com.sliide.usermanagement.states.UsersListState


class UsersListViewModel(private val usersListRepository: UsersListRepository) :
    ViewModel() {

    private var usersItem: UsersItem? = null
    private val usersListStates = MutableLiveData<UsersListState>()

    fun getUsersListState() = usersListStates

    fun getUsers() {
        usersListStates.postValue(UsersListState.ShowLoader)
        Coroutines.main {
            try {
                val response = usersListRepository.getUsers()
                if(response.isSuccessful){
                    if (!response.body().isNullOrEmpty()) {
                        usersListStates.postValue(UsersListState.FillUsers(response.body()!!))
                    } else {
                        usersListStates.postValue(UsersListState.ShowToast(Constants.SOME_ERROR_OCCURRED))
                    }
                }else{
                    usersListStates.postValue(UsersListState.ShowToast(Constants.SOME_ERROR_OCCURRED))
                }
            } catch (e: Exception) {
                usersListStates.postValue(UsersListState.ShowError(e))
            }
        }
    }
    fun addUser(name: String, email: String, gender: String) {
        usersListStates.postValue(UsersListState.ShowLoader)
        Coroutines.main {
            try {
                val response = usersListRepository.addNewUser(name,email,gender)
                if(response.isSuccessful){
                    usersListStates.postValue(UsersListState.userAdded)
                }else{
                    usersListStates.postValue(UsersListState.ShowToast(response.message()))
                }
            } catch (e: Exception) {
                usersListStates.postValue(UsersListState.ShowError(e))
            }
        }
    }

    fun deleteUser(id: String) {
        usersListStates.postValue(UsersListState.ShowLoader)
        Coroutines.main {
            try {
                val response = usersListRepository.deleteUser(id)
                if(response.isSuccessful){
                    usersListStates.postValue(UsersListState.userDeleted)
                }else{
                    usersListStates.postValue(UsersListState.ShowToast(Constants.SOME_ERROR_OCCURRED))
                }
            } catch (e: Exception) {
                usersListStates.postValue(UsersListState.ShowError(e))
            }
        }
    }
}