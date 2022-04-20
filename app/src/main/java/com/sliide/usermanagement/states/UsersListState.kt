package com.sliide.usermanagement.states

import com.sliide.usermanagement.models.UsersItem


sealed class UsersListState {

    object ShowLoader : UsersListState()

    class ShowError(val throwable: Throwable) : UsersListState()

    class ShowToast(val message: String) : UsersListState()

    object userAdded : UsersListState()

    class FillUsers(val users: List<UsersItem>) : UsersListState()

    object userDeleted : UsersListState()

}