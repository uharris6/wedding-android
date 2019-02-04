package com.uharris.wedding.presentation.sections.sites


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SitesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var sitesViewModel: SitesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sites, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sitesViewModel = ViewModelProviders.of(this, viewModelFactory).get(SitesViewModel::class.java)

        sitesViewModel.liveData.observe(this, Observer {
            it?.let {
                handleDataState(it)
            }
        })
        sitesViewModel.fetchSites()
    }

    private fun handleDataState(resource: Resource<List<Site>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {

                }
            }
            ResourceState.LOADING -> {

            }
            ResourceState.ERROR -> {
            }
        }
    }

}
