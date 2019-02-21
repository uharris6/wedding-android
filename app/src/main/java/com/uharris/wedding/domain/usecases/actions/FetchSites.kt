package com.uharris.wedding.domain.usecases.actions

import com.uharris.wedding.data.SitesRemote
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.domain.usecases.base.UseCase
import javax.inject.Inject

class FetchSites @Inject constructor(private val sitesRemote: SitesRemote): UseCase<List<Site>, UseCase.None>() {
    override suspend fun run(params: None): Result<List<Site>> {
        return sitesRemote.getSites()
    }
}