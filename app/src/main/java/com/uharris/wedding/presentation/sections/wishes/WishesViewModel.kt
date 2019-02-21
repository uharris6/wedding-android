package com.uharris.wedding.presentation.sections.wishes

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.actions.FetchWishes
import com.uharris.wedding.domain.usecases.actions.SendWish
import com.uharris.wedding.domain.usecases.base.UseCase
import com.uharris.wedding.presentation.base.BaseViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import javax.inject.Inject

class WishesViewModel @Inject constructor(
    private val fetchWishes: FetchWishes,
    private val sendWish: SendWish,
    private val id: String,
    application: Application): BaseViewModel(application) {

    val liveData: MutableLiveData<Resource<List<Wish>>> = MutableLiveData()
    val wishLiveData: MutableLiveData<Resource<Wish>> = MutableLiveData()

    fun fetchWishes(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        fetchWishes(UseCase.None()){
            when(it) {
                is Result.Success -> handleWishList(it.data)
                is Result.Error -> handleFailure(it.exception)
            }
        }
        return
    }

    fun sendWish(wish: String) {
        wishLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        sendWish(SendWish.Params( id, wish)){
            when(it) {
                is Result.Success -> handleWishSent(it.data)
                is Result.Error -> handleFailure(it.exception)
            }
        }
        return
    }

    private fun handleWishList(wishes: List<Wish>) {
        liveData.postValue(Resource(ResourceState.SUCCESS, wishes, null))
    }

    private fun handleWishSent(wish: Wish) {
        wishLiveData.postValue(Resource(ResourceState.SUCCESS, wish, null))
    }
}