package com.uharris.wedding.presentation.sections.wishes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.usecases.actions.FetchWishes
import com.uharris.wedding.domain.usecases.actions.SendWish
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class WishesViewModel @Inject constructor(
    private val fetchWishes: FetchWishes,
    private val sendWish: SendWish,
    application: Application): AndroidViewModel(application) {

    val liveData: MutableLiveData<Resource<List<Wish>>> = MutableLiveData()
    val wishLiveData: MutableLiveData<Resource<Wish>> = MutableLiveData()

    fun fetchWishes(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))

        return fetchWishes.execute(FetchWishesSubscriber())
    }

    fun sendWish(wish: String) {
        wishLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val params = SendWish.Params("5c4f7472e6b5b20017591d45", wish)
        return sendWish.execute(SendWishSubscriber(), params)
    }

    override fun onCleared() {
        fetchWishes.dispose()
        sendWish.dispose()
        super.onCleared()
    }

    inner class FetchWishesSubscriber : DisposableObserver<List<Wish>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Wish>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    inner class SendWishSubscriber : DisposableObserver<Wish>() {
        override fun onComplete() {}

        override fun onNext(t: Wish) {
            wishLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            wishLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }
}