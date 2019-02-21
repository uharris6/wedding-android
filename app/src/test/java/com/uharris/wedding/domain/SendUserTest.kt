package com.uharris.wedding.domain

import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import com.uharris.wedding.domain.usecases.actions.SendUser
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.factory.UtilsFactory
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SendUserTest {

    @Mock
    private lateinit var userRemote: UserRemote

    private lateinit var sendUser: SendUser

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        sendUser = SendUser(userRemote)
    }

    @Test
    fun sendUser() {
        runBlocking {
            val body = UtilsFactory.makeUserBody()
            stubUserRemoteSendUser(body, Result.Success(ResponseFactory.makeUserResponse()))

            sendUser.run(SendUser.Params(body.firstName, body.nickname, body.lastName))
            verify(userRemote).registerUser(body)
            verifyNoMoreInteractions(userRemote)
        }
    }

    private suspend fun stubUserRemoteSendUser(body: UserBody, userResult: Result<User>) {
        whenever(userRemote.registerUser(body)).thenReturn(userResult)
    }
}