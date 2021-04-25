package uk.ac.tees.mad.w9501736.di.components;


import android.app.Application;

import dagger.Component;
import uk.ac.tees.mad.w9501736.cache.GuestData;
import uk.ac.tees.mad.w9501736.di.module.WiseLiModule;
import uk.ac.tees.mad.w9501736.di.module.WiseLiViewModelModule;
import uk.ac.tees.mad.w9501736.di.scopes.WiseLiScope;

@WiseLiScope
@Component(
        modules = {
                WiseLiModule.class,
                WiseLiViewModelModule.class
        }
)
public interface WiseLiComponent {

    Application application();

    GuestData provideGuestData();

    //RetrofitHelper provideRetrofitHelper();

    //RemoteConfig provideRemoteConfig();

    //WiseLiApiClient providerWiseLiApiClient();

    //ViewModelProvider.Factory provideViewModelFactory();

    //void inject(RegisterFragment registerFragment);

    //void inject(SplashScreenFragment splashScreenFragment);

    //SplashScreenFragmentViewModel provideSplashScreenFragmentViewModel();

    //RegisterFragmentViewModel provideRegisterFragmentViewModel();


}
