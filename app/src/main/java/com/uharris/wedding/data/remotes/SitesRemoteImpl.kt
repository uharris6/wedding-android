package com.uharris.wedding.data.remotes

import com.uharris.wedding.data.SitesRemote
import com.uharris.wedding.data.base.BaseRepository
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.data.base.Result
import com.uharris.wedding.data.functional.Either
import com.uharris.wedding.data.services.SitesService
import com.uharris.wedding.domain.model.Site
import com.uharris.wedding.presentation.base.NetworkHandler
import java.io.IOException
import javax.inject.Inject

class SitesRemoteImpl @Inject constructor(private val service: SitesService,
                                          private val networkHandler: NetworkHandler): BaseRepository(), SitesRemote {
    override suspend fun getSites():Result<List<Site>> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(service.getSites(), default = emptyList())
            false, null -> Result.Error(IOException("Error connecting to the network"))
        }
    }
}