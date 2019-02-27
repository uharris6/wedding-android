package com.uharris.wedding.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.usecases.actions.UploadPhoto
import com.uharris.wedding.factory.DataFactory
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.presentation.sections.photos.add.AddPhotoViewModel
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
class AddPhotoViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var uploadPhoto: UploadPhoto
    @Mock
    private lateinit var observer: Observer<Resource<Photo>>

    @Captor
    private lateinit var captor: KArgumentCaptor<(Result<Photo>) -> Unit>

    private lateinit var addPhotoViewModel: AddPhotoViewModel

    @Before
    fun setup(){
        uploadPhoto = mock()
        observer = mock()

        captor = argumentCaptor()

        val application = Mockito.mock(Application::class.java)

        addPhotoViewModel = AddPhotoViewModel(uploadPhoto, application)
    }

    @Test
    fun uploadPhotoReturnLoadingState(){
        addPhotoViewModel.photoLiveData.observeForever(observer)

        addPhotoViewModel.uploadPhoto(DataFactory.randomString(), DataFactory.randomString())

        assert(addPhotoViewModel.photoLiveData.value?.status == ResourceState.LOADING)
    }

    @Test
    fun uploadPhotoReturnSuccessState(){
        val data = ResponseFactory.makePhotoResponse()
        val result = Result.Success(data)

        addPhotoViewModel.photoLiveData.observeForever(observer)

        addPhotoViewModel.uploadPhoto(DataFactory.randomString(), DataFactory.randomString())

        runBlocking { verify(uploadPhoto).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(addPhotoViewModel.photoLiveData.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun uploadPhotoReturnSuccessData(){
        val data = ResponseFactory.makePhotoResponse()
        val result = Result.Success(data)

        addPhotoViewModel.photoLiveData.observeForever(observer)

        addPhotoViewModel.uploadPhoto(DataFactory.randomString(), DataFactory.randomString())

        runBlocking { verify(uploadPhoto).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(addPhotoViewModel.photoLiveData.value?.data == data)
    }
}