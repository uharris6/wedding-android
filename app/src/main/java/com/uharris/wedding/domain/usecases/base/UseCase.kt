package com.uharris.wedding.domain.usecases.base

import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = GlobalScope.async {
            run(params)
        }
        GlobalScope.launch {
            onResult(job.await())
        }
    }

    class None
}