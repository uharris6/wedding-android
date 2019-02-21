package com.uharris.wedding.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.UploadPhoto
import com.uharris.wedding.factory.DataFactory
import com.uharris.wedding.factory.ResponseFactory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UploadPhotoTest {

    @Mock
    private lateinit var photosRemote: PhotosRemote

    private lateinit var uploadPhoto: UploadPhoto

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        uploadPhoto = UploadPhoto(photosRemote)
    }

    @Test
    fun uploadPhoto(){
        runBlocking {
            val data = Result.Success(ResponseFactory.makePhotoResponse())
            val title = DataFactory.randomString()
            val path = DataFactory.randomString()

            stubPhotosRemoteUploadPhoto(title, path, data)

            uploadPhoto.run(UploadPhoto.Params(title, path))

            verify(photosRemote).sendPhoto(title, path)
            verifyNoMoreInteractions(photosRemote)
        }
    }

    private suspend fun stubPhotosRemoteUploadPhoto(title: String, path: String, result: Result<Photo>) {
        whenever(photosRemote.sendPhoto(title, path)).thenReturn(result)
    }
}