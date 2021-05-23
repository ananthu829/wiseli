package uk.ac.tees.mad.w9501736.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import uk.ac.tees.mad.w9501736.di.scopes.ViewModelKey;
import uk.ac.tees.mad.w9501736.ui.WiseLiViewModelProviderFactory;
import uk.ac.tees.mad.w9501736.ui.viewModel.splashScreen.SplashScreenFragmentViewModel;

@Module
public abstract class WiseLiViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(WiseLiViewModelProviderFactory wiseLiViewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenFragmentViewModel.class)
    public abstract ViewModel bindSplashScreenFragmentViewModel(SplashScreenFragmentViewModel splashScreenFragmentViewModel);
}
