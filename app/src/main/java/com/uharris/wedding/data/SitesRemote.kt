package com.uharris.wedding.data

import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Site

interface SitesRemote {

    suspend fun getSites(): Either<Failure, List<Site>>
}