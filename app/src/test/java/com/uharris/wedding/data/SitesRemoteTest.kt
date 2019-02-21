package com.uharris.wedding.data

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.remotes.SitesRemoteImpl
import com.uharris.wedding.data.services.SitesService
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.factory.UtilsFactory
import com.uharris.wedding.presentation.base.NetworkHandler
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
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
class SitesRemoteTest {

    @Mock
    private lateinit var service: SitesService
    @Mock
    private lateinit var networkHandler: NetworkHandler

    private lateinit var sitesRemote: SitesRemote

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        sitesRemote = SitesRemoteImpl(service, networkHandler)
    }

    @Test
    fun getSitesReturnData(){
        val data = UtilsFactory.makeSiteList()
        val deferred = CompletableDeferred<Response<List<Site>>>()
        deferred.complete(Response.success(data))

        stubServiceGetSites(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val sites = runBlocking { sitesRemote.getSites() }

        Result.Success(data) shouldEqual sites
    }

    @Test
    fun getSitesNetworkFailure(){
        given { networkHandler.isConnected }.willReturn(false)

        runBlocking { sitesRemote.getSites() }

        verifyZeroInteractions(service)
    }

    @Test
    fun getSitesNetworkFailureNull(){
        given { networkHandler.isConnected }.willReturn(null)

        runBlocking { sitesRemote.getSites() }

        verifyZeroInteractions(service)
    }

    @Test
    fun getSitesServerError(){
        val deferred = CompletableDeferred<Response<List<Site>>>()
        deferred.complete(Response.error(400, ResponseBody.create(okhttp3.MultipartBody.FORM, "Error")))
        stubServiceGetSites(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val sites = runBlocking { sitesRemote.getSites() }

        Result.Success(mutableListOf<Site>()) shouldEqual sites
    }

    private fun stubServiceGetSites(siteDeferred: Deferred<Response<List<Site>>>) {
        whenever(service.getSites())
            .thenReturn(siteDeferred)
    }
}