package com.uharris.wedding.presentation.sections.sites


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.uharris.wedding.R
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.presentation.base.BaseFragment
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.sections.sites.detail.SiteDetailActivity
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_sites.*
import javax.inject.Inject

class SitesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var sitesViewModel: SitesViewModel

    private lateinit var adapter: SitesAdapter

    var sites = mutableListOf<Site>()

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

        sitesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SitesAdapter(sites) {
            SiteDetailActivity.startActivity((context as Activity), it)
        }
        sitesRecyclerView.adapter = adapter

        sitesViewModel.liveData.observe(this, Observer {
            handleDataState(it)
        })

        sitesViewModel.failure.observe(this, Observer {
            handleFailure(it)
        })
        sitesViewModel.fetchSites()
    }

    private fun handleDataState(resource: Resource<List<Site>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    sites.clear()
                    sites.addAll(it)
                    adapter.setItems(sites)
                }
            }
            ResourceState.LOADING -> {

            }
            ResourceState.ERROR -> {
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        showMessage(failure.toString())
    }
}
