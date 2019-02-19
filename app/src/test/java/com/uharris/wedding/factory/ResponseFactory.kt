package com.uharris.wedding.factory

import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.domain.model.Wish

object ResponseFactory {

    fun makeUserResponse(): User {
        return User(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeSiteResponse(): Site {
        return Site(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomDouble(), DataFactory.randomDouble(),
            DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeWishResponse(): Wish {
        return Wish(makeUserResponse(), DataFactory.randomString())
    }

    fun makePhotoResponse(): Photo {
        return Photo(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString())
    }
}