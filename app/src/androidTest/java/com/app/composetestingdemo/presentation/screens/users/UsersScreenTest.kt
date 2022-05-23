package com.app.composetestingdemo.presentation.screens.users

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.composetestingdemo.data.di.DataModule
import com.app.composetestingdemo.data.di.DispatchersModule
import com.app.composetestingdemo.data.local.UserEntity
import com.app.composetestingdemo.data.local.UsersDatabase
import com.app.composetestingdemo.presentation.MainActivity
import com.app.composetestingdemo.presentation.navigation.AppScreen
import com.app.composetestingdemo.presentation.theme.ComposeTestingDemoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(DataModule::class, DispatchersModule::class)
class UsersScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var db: UsersDatabase

    fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.onNodeWithTag(tag: String) =
        onNodeWithTag(tag, useUnmergedTree = true)

    @Before
    fun setUp() = runBlockingTest {
        hiltRule.inject()

        db.dao.apply {
            insertUser(
                UserEntity(
                    email = "test1@test.test",
                    password = "12345678q"
                )
            )
            insertUser(
                UserEntity(
                    email = "test2@test.test",
                    password = "12345678q"
                )
            )
            insertUser(
                UserEntity(
                    email = "test3@test.test",
                    password = "12345678q"
                )
            )

        }

        composeRule.setContent {
            val navController = rememberNavController()
            ComposeTestingDemoTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppScreen.UsersList.route
                ) {
                    composable(
                        route = AppScreen.UsersList.route
                    ) {
                        UsersScreen()
                    }
                }
            }
        }
    }

    @Test
    fun clickCard_checkboxVisible() {
        composeRule.onNodeWithTag("Card 1")
            .performClick()
        composeRule.onNodeWithTag("Checkbox 1").assertExists()
    }
}