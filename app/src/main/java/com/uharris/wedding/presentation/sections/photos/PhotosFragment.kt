package com.uharris.wedding.presentation.sections.photos


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.uharris.wedding.R
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.presentation.base.BaseFragment
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.sections.photos.add.AddPhotoActivity
import com.uharris.wedding.presentation.sections.photos.detail.DetailPhotoActivity
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_photos.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class PhotosFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var photosViewModel: PhotosViewModel

    var photos = mutableListOf<Photo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_photos, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_create_photo) {
            startActivityForResult(Intent(context, AddPhotoActivity::class.java), PHOTO)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosViewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotosViewModel::class.java)
        photosRecyclerView.layoutManager = GridLayoutManager(context, 3)
        adapter = PhotosAdapter(photos) {
            DetailPhotoActivity.startActivity((context as Activity), it)
        }
        photosRecyclerView.hasFixedSize()
        photosRecyclerView.setItemViewCacheSize(20)
        photosRecyclerView.adapter = adapter

        photosViewModel.photosLiveData.observe(this, Observer {
            handleDataState(it)
        })

        photosViewModel.failure.observe(this, Observer {
            handleFailure(it)
        })
        photosViewModel.fetchPhotos()
    }

    private fun handleDataState(resource: Resource<List<Photo>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    photos.clear()
                    photos.addAll(it)
                    adapter.setItems(photos)
                }
            }
            ResourceState.LOADING -> {

            }
            ResourceState.ERROR -> {
            }
        }
    }

    private fun handleFailure(failure: String) {
        showMessage(failure)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }

        if (requestCode == PHOTO) {
            if(resultCode == Activity.RESULT_OK) {
                val photo = data?.getParcelableExtra<Photo>(PHOTO_EXTRA)
                photos.add(photo ?: Photo())
                adapter.setItems(photos)
            }
        }
    }

    companion object {
        const val PHOTO = 12345
        const val PHOTO_EXTRA = "extra"
    }
}
