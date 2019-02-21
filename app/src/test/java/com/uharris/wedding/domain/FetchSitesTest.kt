package com.uharris.wedding.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.wedding.data.SitesRemote
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.usecases.actions.FetchSites
import com.uharris.wedding.domain.usecases.base.UseCase
import com.uharris.wedding.factory.UtilsFactory
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchSitesTest {

    @Mock
    private lateinit var sitesRemote: SitesRemote

    private lateinit var fetchSites: FetchSites

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        fetchSites = FetchSites(sitesRemote)
    }

    @Test
    fun fetchSites() {
        runBlocking {
            val data = Result.Success(UtilsFactory.makeSiteList())

            stubSitesRemoteFetchSites(data)

            fetchSites.run(UseCase.None())
            verify(sitesRemote).getSites()
            verifyNoMoreInteractions(sitesRemote)
        }
    }

    private suspend fun stubSitesRemoteFetchSites(result: Result<List<Site>>) {
        whenever(sitesRemote.getSites()).thenReturn(result)
    }
}