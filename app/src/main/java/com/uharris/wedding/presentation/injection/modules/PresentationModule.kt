package com.uharris.wedding.presentation.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uharris.wedding.presentation.base.BaseViewModel
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.sections.photos.PhotosViewModel
import com.uharris.wedding.presentation.sections.photos.add.AddPhotoViewModel
import com.uharris.wedding.presentation.sections.register.RegisterViewModel
import com.uharris.wedding.presentation.sections.sites.SitesViewModel
import com.uharris.wedding.presentation.sections.wishes.WishesViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WishesViewModel::class)
    abstract fun bindWishesViewModel(viewModel: WishesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SitesViewModel::class)
    abstract fun bindSitesViewModel(viewModel: SitesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindPhotosViewModel(viewModel: PhotosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPhotoViewModel::class)
    abstract fun bindAddPhotoViewModel(viewModel: AddPhotoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)