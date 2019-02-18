package com.uharris.wedding.presentation.injection.modules

import com.uharris.wedding.presentation.sections.main.MainActivity
import com.uharris.wedding.presentation.sections.photos.PhotosFragment
import com.uharris.wedding.presentation.sections.photos.add.AddPhotoActivity
import com.uharris.wedding.presentation.sections.register.RegisterActivity
import com.uharris.wedding.presentation.sections.sites.SitesFragment
import com.uharris.wedding.presentation.sections.splash.SplashActivity
import com.uharris.wedding.presentation.sections.wishes.WishesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributesRegisterActivty(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesWishesFragment(): WishesFragment

    @ContributesAndroidInjector
    abstract fun contributesSitesFragment(): SitesFragment

    @ContributesAndroidInjector
    abstract fun contributesPhotosFragment(): PhotosFragment

    @ContributesAndroidInjector
    abstract fun contributesAddPhotoActivity(): AddPhotoActivity

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity
}