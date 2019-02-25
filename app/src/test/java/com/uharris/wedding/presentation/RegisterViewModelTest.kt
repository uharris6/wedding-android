package com.uharris.wedding.presentation

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.BuildConfig
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.usecases.actions.SendUser
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.presentation.base.BaseActivity
import com.uharris.wedding.presentation.sections.register.RegisterViewModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    application = Application::class
)
class RegisterViewModelTest {

    @Mock
    private lateinit var sendUser: SendUser
    private lateinit var registerViewModel: RegisterViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        registerViewModel = RegisterViewModel(sendUser, RuntimeEnvironment.application)
    }

    @Test
    fun loadUserUpdateLiveData() {
        val data = ResponseFactory.makeUserResponse()

        given { runBlocking { sendUser.run(eq(any())) } }.willReturn(Result.Success(data))

        registerViewModel.liveData.observeForever {
            it.data?.let {
                with(it) {
                    id shouldEqualTo data.id
                    firstName shouldEqualTo data.firstName
                    nickname shouldEqualTo data.nickname
                    lastName shouldEqualTo data.lastName
                }
            }
        }

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, data.lastName, "230219")
    }
}