package com.uharris.wedding.presentation.sections.register

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.usecases.actions.RegisterUser
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    application: Application): AndroidViewModel(application) {

    val liveData: MutableLiveData<Resource<User>> = MutableLiveData()

    fun attempRegisterUser(firstName: String, nickname: String, lastName: String) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))

        if(firstName.isNullOrBlank() || lastName.isNullOrBlank()) {
            return liveData.postValue(Resource(ResourceState.ERROR, null, "Debe rellenar los campos de nombre y apellido"))
        }

        val params = RegisterUser.Params(firstName, nickname, lastName)
        return registerUser.execute(RegisterSubscriber(), params)
    }

    override fun onCleared() {
        registerUser.dispose()
        super.onCleared()
    }

    inner class RegisterSubscriber : DisposableObserver<User>() {
        override fun onComplete() {}

        override fun onNext(value: User) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
            val editor = prefs.edit()
            editor.putString("userId", value.id)
            editor.putString("firstName", value.firstName)
            editor.putString("nickname", value.nickname)
            editor.putString("lastName", value.lastName)
            editor.apply()

            liveData.postValue(Resource(ResourceState.SUCCESS, value, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }
}