package com.uharris.wedding.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.actions.FetchWishes
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
class FetchWishesTest {

    @Mock
    private lateinit var wishesRemote: WishesRemote

    private lateinit var fetchWishes: FetchWishes

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        fetchWishes = FetchWishes(wishesRemote)
    }

    @Test
    fun fetchWishes(){
        runBlocking {
            val data = Result.Success(UtilsFactory.makeWishList())

            stubWishesRemoteFetchWishes(data)

            fetchWishes.run(UseCase.None())

            verify(wishesRemote).getWishes()
            verifyNoMoreInteractions(wishesRemote)
        }
    }

    private suspend fun stubWishesRemoteFetchWishes(result: Result<List<Wish>>) {
        whenever(wishesRemote.getWishes()).thenReturn(result)
    }
}