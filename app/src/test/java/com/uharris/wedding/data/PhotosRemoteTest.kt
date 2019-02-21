package com.uharris.wedding.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.remotes.PhotosRemoteImpl
import com.uharris.wedding.data.services.PhotosService
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.factory.DataFactory
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
class PhotosRemoteTest {

    @Mock
    private lateinit var service: PhotosService
    @Mock
    private lateinit var networkHandler: NetworkHandler

    private lateinit var photosRemote: PhotosRemote

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        photosRemote = PhotosRemoteImpl(networkHandler, service)
    }

    @Test
    fun getPhotosReturnData(){
        val data = UtilsFactory.makePhotoList()
        val deferred = CompletableDeferred<Response<List<Photo>>>()
        deferred.complete(Response.success(data))

        stubServiceGetPhotos(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val photos = runBlocking { photosRemote.getPhotos() }

        Result.Success(data) shouldEqual photos
    }

    @Test
    fun getPhotosNetworkFailure(){
        given { networkHandler.isConnected }.willReturn(false)

        runBlocking { photosRemote.getPhotos() }

        verifyZeroInteractions(service)
    }

    @Test
    fun getPhotosNetworkFailureNull(){
        given { networkHandler.isConnected }.willReturn(null)

        runBlocking { photosRemote.getPhotos() }

        verifyZeroInteractions(service)
    }

    @Test
    fun getPhotosServerError(){
        val deferred = CompletableDeferred<Response<List<Photo>>>()
        deferred.complete(Response.error(400, ResponseBody.create(MultipartBody.FORM, "Error")))

        stubServiceGetPhotos(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val photos = runBlocking { photosRemote.getPhotos() }

        Result.Success(emptyList<Photo>()) shouldEqual photos
    }

    @Test
    fun sendPhotoReturnData(){
        val data = ResponseFactory.makePhotoResponse()
        val deferred = CompletableDeferred<Response<Photo>>()
        deferred.complete(Response.success(data))

        stubServiceUploadPhoto(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val photos = runBlocking { photosRemote.sendPhoto(DataFactory.randomString(), DataFactory.randomString()) }

        Result.Success(data) shouldEqual photos
    }

    @Test
    fun sendPhotoNetworkFailure(){
        given { networkHandler.isConnected }.willReturn(false)

        runBlocking { photosRemote.sendPhoto(DataFactory.randomString(), DataFactory.randomString()) }

        verifyZeroInteractions(service)
    }

    @Test
    fun sendPhotoNetworkFailureNull(){
        given { networkHandler.isConnected }.willReturn(null)

        runBlocking { photosRemote.sendPhoto(DataFactory.randomString(), DataFactory.randomString()) }

        verifyZeroInteractions(service)
    }

    @Test
    fun sendPhotosServerError(){
        val deferred = CompletableDeferred<Response<Photo>>()
        deferred.complete(Response.error(400, ResponseBody.create(MultipartBody.FORM, "Error")))

        stubServiceUploadPhoto(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val photos = runBlocking { photosRemote.sendPhoto(DataFactory.randomString(), DataFactory.randomString()) }

        Result.Success(Photo()) shouldEqual photos
    }

    private fun stubServiceGetPhotos(deferred: Deferred<Response<List<Photo>>>) {
        whenever(service.getPhotos()).thenReturn(deferred)
    }

    private fun stubServiceUploadPhoto(deferred: Deferred<Response<Photo>>) {
        whenever(service.sendPhoto(any(), any())).thenReturn(deferred)
    }
}