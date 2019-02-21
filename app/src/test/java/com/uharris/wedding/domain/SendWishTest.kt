package com.uharris.wedding.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.WishBody
import com.uharris.wedding.domain.usecases.actions.SendWish
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.factory.UtilsFactory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SendWishTest {

    @Mock
    private lateinit var wishesRemote: WishesRemote

    private lateinit var sendWish: SendWish

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        sendWish = SendWish(wishesRemote)
    }

    @Test
    fun sendWish(){
        runBlocking {
            val data = ResponseFactory.makeWishResponse()
            val body = UtilsFactory.makeWishBody()

            stubWishesRemoteSendWish(body, Result.Success(data))

            sendWish.run(SendWish.Params(body.user, body.comment))

            verify(wishesRemote).sendWish(body)
            verifyNoMoreInteractions(wishesRemote)
        }
    }

    private suspend fun stubWishesRemoteSendWish(body: WishBody, result: Result<Wish>){
        whenever(wishesRemote.sendWish(body)).thenReturn(result)
    }
}

