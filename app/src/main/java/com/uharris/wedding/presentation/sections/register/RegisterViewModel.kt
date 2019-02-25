package com.uharris.wedding.presentation.sections.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.usecases.actions.SendUser
import com.uharris.wedding.presentation.base.BaseViewModel
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val sendUser: SendUser,
    application: Application
) : BaseViewModel(application) {

    val liveData: MutableLiveData<Resource<User>> = MutableLiveData()

    fun attemptRegisterUser(firstName: String, nickname: String, lastName: String, code: String) {
//        liveData.postValue(Resource(ResourceState.LOADING, null, null))

        if(!code.isNullOrBlank() && code.toLowerCase() == "230219"){
            if (firstName.isNullOrBlank() || lastName.isNullOrBlank()) {
                return liveData.postValue(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        "Debe rellenar los campos de nombre y apellido."
                    )
                )
            } else {
                sendUser(SendUser.Params(firstName, nickname, lastName)) {
                    when(it) {
                        is Result.Success -> handleUserSent(it.data)
                        is Result.Error -> handleFailure(it.exception)
                    }
                }
            }
        } else {
            return liveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    null,
                    "El código ingresado es incorrecto."
                )
            )
        }



        return
    }

    private fun handleUserSent(user: User) {
        liveData.postValue(Resource(ResourceState.SUCCESS, user, null))
    }
}