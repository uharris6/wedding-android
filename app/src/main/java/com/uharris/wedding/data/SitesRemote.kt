package com.uharris.wedding.data

import com.uharris.wedding.data.base.Result
import com.uharris.wedding.domain.model.Site

interface SitesRemote {

    suspend fun getSites(): Result<List<Site>>
}