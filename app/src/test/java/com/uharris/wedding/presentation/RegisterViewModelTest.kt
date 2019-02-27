package com.uharris.wedding.presentation

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.usecases.actions.SendUser
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.presentation.sections.register.RegisterViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RegisterViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var sendUser: SendUser
    @Mock
    private lateinit var observer: Observer<Resource<User>>
    private lateinit var registerViewModel: RegisterViewModel

    @Captor
    private lateinit var captor: KArgumentCaptor<(Result<User>) -> Unit>

    @Before
    fun setup() {
        sendUser = mock()
        observer = mock()

        captor = argumentCaptor()

        val applicationMock = Mockito.mock(Application::class.java)
        registerViewModel = RegisterViewModel(sendUser, applicationMock)
    }

    @Test
    fun loadUserReturnLoadingState() {
        val data = ResponseFactory.makeUserResponse()

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, data.lastName, "230219")

        assert(registerViewModel.liveData.value?.status == ResourceState.LOADING)
    }


    @Test
    fun loadUserReturnSuccessState() {
        val data = ResponseFactory.makeUserResponse()
        val result = Result.Success(data)

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, data.lastName, "230219")

        runBlocking { verify(sendUser).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(registerViewModel.liveData.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun loadUserReturnSuccessData() {
        val data = ResponseFactory.makeUserResponse()
        val result = Result.Success(data)

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, data.lastName, "230219")

        runBlocking { verify(sendUser).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(registerViewModel.liveData.value?.data == data)
    }

    @Test
    fun loadUserReturnErrorEmptyCodeMessage(){
        val data = ResponseFactory.makeUserResponse()

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, data.lastName, "")

        assert(registerViewModel.liveData.value?.status == ResourceState.ERROR)
    }

    @Test
    fun loadUserReturnErrorWrongCodeMessage(){
        val data = ResponseFactory.makeUserResponse()

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, data.lastName, "23")

        assert(registerViewModel.liveData.value?.status == ResourceState.ERROR)
    }

    @Test
    fun loadUserReturnErrorEmptyNameMessage(){
        val data = ResponseFactory.makeUserResponse()

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser("", data.nickname, data.lastName, "230219")

        assert(registerViewModel.liveData.value?.status == ResourceState.ERROR)
    }

    @Test
    fun loadUserReturnErrorEmptyLastNameMessage(){
        val data = ResponseFactory.makeUserResponse()

        registerViewModel.liveData.observeForever(observer)

        registerViewModel.attemptRegisterUser(data.firstName, data.nickname, "", "230219")

        assert(registerViewModel.liveData.value?.status == ResourceState.ERROR)
    }
}