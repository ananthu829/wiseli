package uk.ac.tees.mad.w9501736.di.components;


import androidx.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Component;
import uk.ac.tees.mad.w9501736.di.module.WiseLiViewModelModule;
import uk.ac.tees.mad.w9501736.ui.fragment.splashScreen.SplashScreenFragment;
import uk.ac.tees.mad.w9501736.ui.viewModel.splashScreen.SplashScreenFragmentViewModel;

@Singleton
@Component(
        modules = WiseLiViewModelModule.class
)
public interface WiseLiComponent {

    ViewModelProvider.Factory provideViewModelFactory();

    void inject(SplashScreenFragment splashScreenFragment);

    SplashScreenFragmentViewModel provideSplashScreenFragmentViewModel();

    //GuestData provideGuestData();

    //RetrofitHelper provideRetrofitHelper();

    //RemoteConfig provideRemoteConfig();

    //WiseLiApiClient providerWiseLiApiClient();

    //ViewModelProvider.Factory provideViewModelFactory();

    //void inject(RegisterFragment registerFragment);

    //void inject(SplashScreenFragment splashScreenFragment);

    //SplashScreenFragmentViewModel provideSplashScreenFragmentViewModel();

    //RegisterFragmentViewModel provideRegisterFragmentViewModel();


}
