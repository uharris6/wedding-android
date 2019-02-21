package com.uharris.wedding.presentation.base

import android.content.Context
import com.uharris.wedding.extension.networkInfo
import com.uharris.wedding.utils.Mockable
import javax.inject.Inject
import javax.inject.Singleton

@Mockable
@Singleton
open class NetworkHandler
@Inject constructor(private val context: Context) {
    open val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}