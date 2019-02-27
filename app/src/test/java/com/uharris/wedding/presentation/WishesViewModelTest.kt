package com.uharris.wedding.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.actions.FetchWishes
import com.uharris.wedding.domain.usecases.actions.SendWish
import com.uharris.wedding.factory.DataFactory
import com.uharris.wedding.factory.ResponseFactory
import com.uharris.wedding.factory.UtilsFactory
import com.uharris.wedding.presentation.sections.wishes.WishesViewModel
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
class WishesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchWishes: FetchWishes
    @Mock
    private lateinit var sendWish: SendWish
    @Mock
    private lateinit var observerWish: Observer<Resource<List<Wish>>>
    @Mock
    private lateinit var observerSendWish: Observer<Resource<Wish>>

    @Captor
    private lateinit var captorWish: KArgumentCaptor<(Result<List<Wish>>) -> Unit>
    @Captor
    private lateinit var captorSendWish: KArgumentCaptor<(Result<Wish>) -> Unit>

    private lateinit var wishesViewModel: WishesViewModel

    private var id = DataFactory.randomString()

    @Before
    fun setup(){
        fetchWishes = mock()
        sendWish = mock()

        observerWish = mock()
        observerSendWish = mock()

        captorWish = argumentCaptor()
        captorSendWish = argumentCaptor()

        val application = Mockito.mock(Application::class.java)
        wishesViewModel = WishesViewModel(fetchWishes, sendWish, id, application)
    }

    @Test
    fun fetchWishesReturnLoadingState(){
        wishesViewModel.liveData.observeForever(observerWish)

        wishesViewModel.fetchWishes()

        assert(wishesViewModel.liveData.value?.status == ResourceState.LOADING)
    }

    @Test
    fun fetchWishesReturnSuccessState(){
        val result = Result.Success(UtilsFactory.makeWishList())
        wishesViewModel.liveData.observeForever(observerWish)

        wishesViewModel.fetchWishes()

        runBlocking { verify(fetchWishes).invoke(any(), captorWish.capture())}
        captorWish.firstValue.invoke(result)

        assert(wishesViewModel.liveData.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun fetchWishesReturnSuccessData(){
        val data = UtilsFactory.makeWishList()
        val result = Result.Success(data)
        wishesViewModel.liveData.observeForever(observerWish)

        wishesViewModel.fetchWishes()

        runBlocking { verify(fetchWishes).invoke(any(), captorWish.capture())}
        captorWish.firstValue.invoke(result)

        assert(wishesViewModel.liveData.value?.data == data)
    }

    @Test
    fun sendWishReturnLoadingState(){
        wishesViewModel.wishLiveData.observeForever(observerSendWish)

        wishesViewModel.sendWish(DataFactory.randomString())

        assert(wishesViewModel.wishLiveData.value?.status == ResourceState.LOADING)
    }

    @Test
    fun sendWishReturnSuccessState(){
        val result = Result.Success(ResponseFactory.makeWishResponse())
        wishesViewModel.wishLiveData.observeForever(observerSendWish)

        wishesViewModel.sendWish(DataFactory.randomString())

        runBlocking { verify(sendWish).invoke(any(), captorSendWish.capture())}
        captorSendWish.firstValue.invoke(result)

        assert(wishesViewModel.wishLiveData.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun sendWishReturnSuccessData(){
        val data = ResponseFactory.makeWishResponse()
        val result = Result.Success(data)
        wishesViewModel.wishLiveData.observeForever(observerSendWish)

        wishesViewModel.sendWish(DataFactory.randomString())

        runBlocking { verify(sendWish).invoke(any(), captorSendWish.capture())}
        captorSendWish.firstValue.invoke(result)

        assert(wishesViewModel.wishLiveData.value?.data == data)
    }
}