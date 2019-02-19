package com.uharris.wedding.factory

import com.uharris.wedding.domain.model.body.UserBody
import com.uharris.wedding.domain.model.body.WishBody

object UtilsFactory {

    fun makeUserBody(): UserBody {
        return UserBody(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeWishBody(): WishBody {
        return WishBody(DataFactory.randomString(), DataFactory.randomString())
    }
}