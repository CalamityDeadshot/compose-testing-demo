package com.app.composetestingdemo.presentation.screens.users

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@ExperimentalMaterialApi
@Composable
fun UsersScreen(
    usersViewModel: UsersViewModel = hiltViewModel(),
) {
    val state = usersViewModel.state

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = state.usersList,
            key = { it.id }
        ) { user ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    usersViewModel.onUserSelected(user.id)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.h4
                    )

                    Row {
                        Text(
                            text = "#${user.id}",
                            style = MaterialTheme.typography.subtitle1
                        )
                        AnimatedVisibility(visible = state.selectedUserIds.any { it == user.id }) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = null
                            )
                        }
                    }
                }
            }
        }
    }
}