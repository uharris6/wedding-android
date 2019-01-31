package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.domain.executor.PostExecutionThread
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.body.UserBody
import com.uharris.wedding.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val userRemote: UserRemote,
    postExecutionThread: PostExecutionThread):
    ObservableUseCase<User, RegisterUser.Params?>(postExecutionThread){
    override fun buildUseCaseObservable(params: RegisterUser.Params?): Observable<User> {
        if(params == null) throw IllegalArgumentException("Params can't be null!")
        return userRemote.registerUser(UserBody(params.firstName, params.nickname, params.lastName))
    }

    data class Params constructor(val firstName: String, val nickname: String, val lastName: String) {
        companion object {
            fun RegisterUser(firstName: String, nickname: String, lastName: String): Params {
                return Params(firstName, nickname, lastName)
            }
        }
    }

}