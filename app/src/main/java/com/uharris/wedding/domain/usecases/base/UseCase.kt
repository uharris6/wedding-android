package com.uharris.wedding.domain.usecases.base

import com.uharris.wedding.data.base.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    operator fun invoke(params: Params, onResult: (Result<Type>) -> Unit = {}) {
        val job = GlobalScope.async {
            run(params)
        }
        GlobalScope.launch {
            onResult(job.await())
        }
    }

    class None
}