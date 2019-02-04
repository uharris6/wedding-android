package com.uharris.wedding.presentation.sections.wishes


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_wishes.*
import javax.inject.Inject

class WishesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var wishesViewModel: WishesViewModel

    private lateinit var adapter: WishesAdapter
    var wishes = mutableListOf<Wish>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishes, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wishesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = WishesAdapter(wishes) {

        }
        wishesRecyclerView.adapter = adapter

        wishesViewModel = ViewModelProviders.of(this, viewModelFactory).get(WishesViewModel::class.java)

        wishesViewModel.liveData.observe(this, Observer {
            it?.let {
                handleDataState(it)
            }
        })
        wishesViewModel.fetchWishes()
    }

    private fun handleDataState(resource: Resource<List<Wish>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    wishes.clear()
                    wishes.addAll(it)
                    adapter.setItems(wishes)
                }
            }
            ResourceState.LOADING -> {

            }
            ResourceState.ERROR -> {
            }
        }
    }
}
