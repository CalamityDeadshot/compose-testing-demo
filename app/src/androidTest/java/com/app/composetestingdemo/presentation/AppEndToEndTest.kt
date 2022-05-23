package com.app.composetestingdemo.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.composetestingdemo.common.TestTags
import com.app.composetestingdemo.data.di.DataModule
import com.app.composetestingdemo.data.di.DispatchersModule
import com.app.composetestingdemo.data.local.UserEntity
import com.app.composetestingdemo.data.local.UsersDatabase
import com.app.composetestingdemo.presentation.navigation.AppScreen
import com.app.composetestingdemo.presentation.screens.registration.RegistrationScreen
import com.app.composetestingdemo.presentation.screens.users.UsersScreen
import com.app.composetestingdemo.presentation.theme.ComposeTestingDemoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(DataModule::class, DispatchersModule::class)
class AppEndToEndTest {

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
            for (i in 1..25) {
                insertUser(
                    UserEntity(
                        email = "test$i@test.test",
                        password = "12345678q"
                    )
                )
            }


        }

        composeRule.setContent {
            val navController = rememberNavController()
            ComposeTestingDemoTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppScreen.Register.route
                ) {
                    composable(
                        route = AppScreen.Register.route
                    ) {
                        RegistrationScreen(
                            navHostController = navController
                        )
                    }

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
    fun login_clicking_checkboxesVisible() = runBlockingTest {
        composeRule.apply {
            onNodeWithTag(TestTags.EMAIL_FIELD_TEST_TAG)
                .performTextInput("test3@test.test")
            onNodeWithTag(TestTags.LOGIN_TOGGLE_TEST_TAG)
                .performClick()
            onNodeWithTag(TestTags.PASSWORD_FIELD_TEST_TAG)
                .performTextInput("12345678q")
            onNodeWithTag(TestTags.SUBMIT_TEST_TAG)
                .performClick()

            val users = db.dao.getUsers().first()

            for (i in users) {
                onNodeWithTag("Card ${i.id}")
                    .performClick()
                if (i.id!!.toInt() % 5 == 0) {
                    onNodeWithTag(TestTags.USERS_LIST_TEST_TAG)
                        .performScrollToKey(i.id!!)
                }
            }
            onNodeWithTag(TestTags.USERS_LIST_TEST_TAG)
                .performScrollToKey(users.first().id!!)
            for (i in users) {
                onNodeWithTag("Checkbox ${i.id}")
                    .assertIsDisplayed()
                if (i.id!!.toInt() % 5 == 0) {
                    onNodeWithTag(TestTags.USERS_LIST_TEST_TAG)
                        .performScrollToKey(i.id!!)
                }
            }
        }
    }
}