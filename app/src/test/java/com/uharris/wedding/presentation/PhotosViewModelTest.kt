package com.uharris.wedding.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.FetchPhotos
import com.uharris.wedding.domain.usecases.base.UseCase
import com.uharris.wedding.factory.UtilsFactory
import com.uharris.wedding.presentation.sections.photos.PhotosViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PhotosViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchPhotos: FetchPhotos
    @Mock
    private lateinit var observer: Observer<Resource<List<Photo>>>
    private lateinit var photosViewModel: PhotosViewModel

    @Captor
    private lateinit var captor: KArgumentCaptor<(Result<List<Photo>>) -> Unit>

    @Before
    fun setup(){
        fetchPhotos = mock()
        observer = mock()

        captor = argumentCaptor()

        val applicationMock = Mockito.mock(Application::class.java)

        photosViewModel = PhotosViewModel(fetchPhotos, applicationMock)
    }

    @Test
    fun fetchPhotosReturnLoadingState(){
        photosViewModel.photosLiveData.observeForever(observer)

        photosViewModel.fetchPhotos()

        assert(photosViewModel.photosLiveData.value?.status == ResourceState.LOADING)
    }

    @Test
    fun fetchPhotosReturnSuccessState(){
        val data = UtilsFactory.makePhotoList()
        val result = Result.Success(data)

        photosViewModel.photosLiveData.observeForever(observer)

        photosViewModel.fetchPhotos()

        runBlocking { verify(fetchPhotos).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(photosViewModel.photosLiveData.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun fetchPhotosReturnSuccessData(){
        val data = UtilsFactory.makePhotoList()
        val result = Result.Success(data)

        photosViewModel.photosLiveData.observeForever(observer)

        photosViewModel.fetchPhotos()

        runBlocking { verify(fetchPhotos).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(photosViewModel.photosLiveData.value?.data == data)
    }
}