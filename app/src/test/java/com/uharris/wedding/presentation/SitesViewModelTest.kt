package com.uharris.wedding.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.usecases.actions.FetchSites
import com.uharris.wedding.factory.UtilsFactory
import com.uharris.wedding.presentation.sections.sites.SitesViewModel
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
class SitesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchSites: FetchSites
    @Mock
    private lateinit var observer: Observer<Resource<List<Site>>>
    private lateinit var sitesViewModel: SitesViewModel

    @Captor
    private lateinit var captor: KArgumentCaptor<(Result<List<Site>>) -> Unit>

    @Before
    fun setup() {
        fetchSites = mock()
        observer = mock()

        captor = argumentCaptor()

        val applicationMock = Mockito.mock(Application::class.java)
        sitesViewModel = SitesViewModel(fetchSites, applicationMock)
    }

    @Test
    fun fetchSitesReturnLoadingState() {
        sitesViewModel.liveData.observeForever(observer)

        sitesViewModel.fetchSites()

        assert(sitesViewModel.liveData.value?.status == ResourceState.LOADING)
    }


    @Test
    fun fetchSitesReturnSuccessState() {
        val data = UtilsFactory.makeSiteList()
        val result = Result.Success(data)

        sitesViewModel.liveData.observeForever(observer)

        sitesViewModel.fetchSites()

        runBlocking { verify(fetchSites).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(sitesViewModel.liveData.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun loadUserReturnSuccessData() {
        val data = UtilsFactory.makeSiteList()
        val result = Result.Success(data)

        sitesViewModel.liveData.observeForever(observer)

        sitesViewModel.fetchSites()

        runBlocking { verify(fetchSites).invoke(any(), captor.capture()) }
        captor.firstValue.invoke(result)

        assert(sitesViewModel.liveData.value?.data == data)
    }
}