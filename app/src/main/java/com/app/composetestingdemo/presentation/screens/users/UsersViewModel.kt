package com.app.composetestingdemo.presentation.screens.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composetestingdemo.domain.use_cases.common.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsers: GetUsers
): ViewModel() {

    var state by mutableStateOf(UsersScreenState())
        private set

    init {
        viewModelScope.launch {
            getUsers().collect {
                state = state.copy(usersList = it)
            }
        }
    }

    fun onUserSelected(userId: Long) {
        val shouldRemoveUser = state.selectedUserIds.contains(userId)
        state = state.copy(
            selectedUserIds = if (!shouldRemoveUser) state.selectedUserIds + userId else state.selectedUserIds - userId
        )
    }
}