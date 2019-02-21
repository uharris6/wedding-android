package com.uharris.wedding.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.remotes.WishesRemoteImpl
import com.uharris.wedding.data.services.WishesService
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.factory.UtilsFactory
import com.uharris.wedding.presentation.base.NetworkHandler
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
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
class WishesRemoteTest {

    @Mock
    private lateinit var service: WishesService
    @Mock
    private lateinit var networkHandler: NetworkHandler

    private lateinit var wishesRemote: WishesRemote

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        wishesRemote = WishesRemoteImpl(networkHandler, service)
    }

    @Test
    fun getWishesReturnData() {
        val data = UtilsFactory.makeWishList()
        val deferred = CompletableDeferred<Response<List<Wish>>>()
        deferred.complete(Response.success(data))

        stubServiceGetWishes(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val wishes = runBlocking { wishesRemote.getWishes() }

        Result.Success(data) shouldEqual wishes
    }

    @Test
    fun getWishesNetworkFailure(){

        given { networkHandler.isConnected }.willReturn(false)

        runBlocking { wishesRemote.getWishes() }

        verifyNoMoreInteractions(service)
    }

    @Test
    fun getWishesNetworkFailureNull(){

        given { networkHandler.isConnected }.willReturn(null)

        runBlocking { wishesRemote.getWishes() }

        verifyNoMoreInteractions(service)
    }

    @Test
    fun getWishesServerFailure() {
        val deferred = CompletableDeferred<Response<List<Wish>>>()
        deferred.complete(Response.error(400, ResponseBody.create(MultipartBody.FORM, "Error")))

        stubServiceGetWishes(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val wishes = runBlocking { wishesRemote.getWishes() }

        Result.Success(emptyList<Wish>()) shouldEqual wishes
    }

    @Test
    fun sendWishReturnData() {
        val data = ResponseFactory.makeWishResponse()
        val deferred = CompletableDeferred<Response<Wish>>()
        deferred.complete(Response.success(data))

        stubServiceSendWish(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val wish = runBlocking { wishesRemote.sendWish(UtilsFactory.makeWishBody()) }

        Result.Success(data) shouldEqual wish
    }

    @Test
    fun sendWishNetworkFailure(){

        given { networkHandler.isConnected }.willReturn(false)

        runBlocking { wishesRemote.sendWish(UtilsFactory.makeWishBody()) }

        verifyNoMoreInteractions(service)
    }

    @Test
    fun sendWishNetworkFailureNull(){

        given { networkHandler.isConnected }.willReturn(null)

        runBlocking { wishesRemote.sendWish(UtilsFactory.makeWishBody()) }

        verifyNoMoreInteractions(service)
    }

    @Test
    fun sendWishServerFailure() {
        val deferred = CompletableDeferred<Response<Wish>>()
        deferred.complete(Response.error(400, ResponseBody.create(MultipartBody.FORM, "Error")))

        stubServiceSendWish(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val wishes = runBlocking { wishesRemote.sendWish(UtilsFactory.makeWishBody()) }

        Result.Success(Wish()) shouldEqual wishes
    }

    private fun stubServiceGetWishes(deferred: Deferred<Response<List<Wish>>>) {
        whenever(service.getWishes()).thenReturn(deferred)
    }

    private fun stubServiceSendWish(deferred: Deferred<Response<Wish>>) {
        whenever(service.sendWish(any())).thenReturn(deferred)
    }
}