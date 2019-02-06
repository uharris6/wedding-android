package com.uharris.wedding.presentation.injection.modules

import com.uharris.wedding.BuildConfig
import com.uharris.wedding.data.PhotosRemote
import com.uharris.wedding.data.SitesRemote
import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.remotes.PhotosRemoteImpl
import com.uharris.wedding.data.remotes.SitesRemoteImpl
import com.uharris.wedding.data.remotes.UserRemoteImpl
import com.uharris.wedding.data.remotes.WishesRemoteImpl
import com.uharris.wedding.data.services.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideUserWeddingService(): UserService {
            return WeddingServiceFactory.makeService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideWishesWeddingService(): WishesService {
            return WeddingServiceFactory.makeService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideSitesWeddingService(): SitesService {
            return WeddingServiceFactory.makeService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun providePhotosWeddingService(): PhotosService {
            return WeddingServiceFactory.makeService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindUserRemote(userRemote: UserRemoteImpl): UserRemote

    @Binds
    abstract fun bindWishesRemote(wishesRemote: WishesRemoteImpl): WishesRemote

    @Binds
    abstract fun bindSitesRemote(sitesRemote: SitesRemoteImpl): SitesRemote

    @Binds
    abstract fun bindPhotosRemote(photosRemote: PhotosRemoteImpl): PhotosRemote
}