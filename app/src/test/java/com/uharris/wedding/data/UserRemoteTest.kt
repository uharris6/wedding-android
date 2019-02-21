package com.uharris.wedding.data

import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.remotes.UserRemoteImpl
import com.uharris.wedding.data.services.UserService
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.factory.UtilsFactory
import com.uharris.wedding.presentation.base.NetworkHandler
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserRemoteTest {

    @Mock
    private lateinit var service: UserService
    @Mock
    private lateinit var networkHandler: NetworkHandler
    private lateinit var remote: UserRemote

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        remote = UserRemoteImpl(networkHandler, service)
    }

    @Test
    fun getUserReturnsData() {
        given { networkHandler.isConnected }.willReturn(true)
        val data = ResponseFactory.makeUserResponse()
        val deferred = CompletableDeferred<Response<User>>()
        deferred.complete(Response.success(data))
        stubServiceSendUser(deferred)

        val user = runBlocking{ remote.registerUser(UtilsFactory.makeUserBody()) }
        Result.Success(data) shouldEqual user
    }

    @Test
    fun getUserNetworkFailure() {
        given { networkHandler.isConnected }.willReturn(false)

        runBlocking{ remote.registerUser(UtilsFactory.makeUserBody()) }

        verifyZeroInteractions(service)
    }

    @Test
    fun getUserNetworkFailureNull() {
        given { networkHandler.isConnected }.willReturn(null)

        runBlocking{ remote.registerUser(UtilsFactory.makeUserBody()) }

        verifyZeroInteractions(service)
    }

    @Test
    fun getUserServerFailure(){
        given { networkHandler.isConnected }.willReturn(true)
        val deferred = CompletableDeferred<Response<User>>()
        deferred.complete(Response.error(400, ResponseBody.create(okhttp3.MultipartBody.FORM, "Error")))
        stubServiceSendUser(deferred)

        var user = runBlocking { remote.registerUser(UtilsFactory.makeUserBody()) }

        Result.Success(User()) shouldEqual user
    }

    private fun stubServiceSendUser(userDeferred: Deferred<Response<User>>?) {
        whenever(service.registerUser(any()))
            .thenReturn(userDeferred)
    }
}