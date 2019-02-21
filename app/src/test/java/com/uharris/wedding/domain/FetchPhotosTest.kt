package com.uharris.wedding.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.FetchPhotos
import com.uharris.wedding.domain.usecases.base.UseCase
import com.uharris.wedding.factory.UtilsFactory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchPhotosTest {

    @Mock
    private lateinit var photosRemote: PhotosRemote

    private lateinit var fetchPhotos: FetchPhotos


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        fetchPhotos = FetchPhotos(photosRemote)
    }

    @Test
    fun fetchPhotos(){
        runBlocking {
            val data = Result.Success(UtilsFactory.makePhotoList())

            stubPhotsRemoteFetchPhotos(data)

            fetchPhotos.run(UseCase.None())

            verify(photosRemote).getPhotos()
            verifyNoMoreInteractions(photosRemote)
        }
    }

    private suspend fun stubPhotsRemoteFetchPhotos(result: Result<List<Photo>>) {
        whenever(photosRemote.getPhotos()).thenReturn(result)
    }

}