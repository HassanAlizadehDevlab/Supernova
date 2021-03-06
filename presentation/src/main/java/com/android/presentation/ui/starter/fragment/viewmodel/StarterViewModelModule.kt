package com.android.presentation.ui.starter.fragment.viewmodel

import androidx.lifecycle.ViewModel
import com.android.presentation.common.di.PerFragment
import com.android.presentation.common.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StarterViewModelModule {

    @Binds
    @PerFragment
    @IntoMap
    @ViewModelKey(StarterViewModel::class)
    abstract fun viewModel(viewModel: StarterViewModel): ViewModel

}