package com.uharris.wedding.presentation.injection.modules

import com.uharris.wedding.BuildConfig
import com.uharris.wedding.data.UserRemote
import com.uharris.wedding.data.WishesRemote
import com.uharris.wedding.data.remotes.UserRemoteImpl
import com.uharris.wedding.data.remotes.WishesRemoteImpl
import com.uharris.wedding.data.services.UserService
import com.uharris.wedding.data.services.WeddingServiceFactory
import com.uharris.wedding.data.services.WishesService
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
    }

    @Binds
    abstract fun bindUserRemote(userRemote: UserRemoteImpl): UserRemote

    @Binds
    abstract fun bindWishesRemote(wishesRemote: WishesRemoteImpl): WishesRemote
}