package com.uharris.wedding.presentation.sections.wishes


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.sections.wishes.create.CreateWishFragment
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_wishes.*
import javax.inject.Inject
import android.view.*
import com.uharris.wedding.presentation.sections.wishes.detail.DetailWishFragment


class WishesFragment : Fragment(), CreateWishFragment.CreateWishListener {
    override fun getWish(wish: String) {
        wishesViewModel.sendWish(wish)
        fragment.dismiss()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var wishesViewModel: WishesViewModel

    private lateinit var adapter: WishesAdapter
    var wishes = mutableListOf<Wish>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_wishes, menu)
    }

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

    private lateinit var fragment: CreateWishFragment

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_create_wish) {
            fragment = CreateWishFragment.newInstance()
            fragment.show(childFragmentManager,"create wish")
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wishesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = WishesAdapter(wishes) {
            val fragment = DetailWishFragment.newInstance(it)
            fragment.show(childFragmentManager, "detail wish")
        }
        wishesRecyclerView.adapter = adapter

        wishesViewModel = ViewModelProviders.of(this, viewModelFactory).get(WishesViewModel::class.java)

        wishesViewModel.liveData.removeObservers(this)
        wishesViewModel.liveData.observe(this, Observer {
            it?.let {
                handleDataState(it)
            }
        })
        wishesViewModel.fetchWishes()

        wishesViewModel.wishLiveData.removeObservers(this)
        wishesViewModel.wishLiveData.observe(this, Observer {
            it?.let {
                handleWishState(it)
            }
        })
    }

    private fun handleWishState(resource: Resource<Wish>) {
        when(resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    wishes.add(it)
                    adapter.setItems(wishes)
                }
            }
            ResourceState.LOADING -> {}
            ResourceState.ERROR -> {}
        }
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
