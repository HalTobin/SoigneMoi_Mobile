package com.example.soignemoi.feature

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.soignemoi.data.api.SessionManagerTest
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.feature.login.presentation.LoginScreenTag
import com.example.soignemoi.ui.LoginTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkModule::class, RepositoryModule::class)
class LoginScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<LoginTestActivity>()
    
    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testLoginScreen() = runTest {
        composeRule.awaitIdle()
        composeRule.onNodeWithTag(LoginScreenTag.MAIL).assert(hasText(SessionManagerTest.DOCTOR_MAIL))
        composeRule.onNodeWithTag(LoginScreenTag.PASSWORD).assert(!hasText(SessionManagerTest.DOCTOR_PASSWORD))
        composeRule.onNodeWithTag(LoginScreenTag.SHOW_PASSWORD).performClick()
        composeRule.awaitIdle()
        composeRule.onNodeWithTag(LoginScreenTag.PASSWORD).assert(hasText(SessionManagerTest.DOCTOR_PASSWORD))
    }

}