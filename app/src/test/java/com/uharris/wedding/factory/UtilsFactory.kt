package com.uharris.wedding.factory

import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.domain.model.body.UserBody
import com.uharris.wedding.domain.model.body.WishBody

object UtilsFactory {

    fun makeUserBody(): UserBody {
        return UserBody(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeWishBody(): WishBody {
        return WishBody(DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeSiteList(): List<Site> {
        val siteList = mutableListOf<Site>()
        siteList.add(ResponseFactory.makeSiteResponse())
        return siteList
    }

    fun makeWishList(): List<Wish> {
        val wishList = mutableListOf<Wish>()
        wishList.add(ResponseFactory.makeWishResponse())
        return wishList
    }

    fun makePhotoList(): List<Photo> {
        val photoList = mutableListOf<Photo>()
        photoList.add(ResponseFactory.makePhotoResponse())
        return photoList
    }
}