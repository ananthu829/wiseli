package uk.ac.tees.mad.w9501736.di.components;


import android.app.Application;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import dagger.Component;
import uk.ac.tees.mad.w9501736.di.module.WiseLiModule;
import uk.ac.tees.mad.w9501736.di.module.WiseLiNetworkDataModule;
import uk.ac.tees.mad.w9501736.di.module.WiseLiSharedDataModule;
import uk.ac.tees.mad.w9501736.di.scopes.WiseLiScope;

@WiseLiScope
@Component(
        modules = {
                WiseLiModule.class,
                WiseLiNetworkDataModule.class,
                WiseLiSharedDataModule.class
        }
)
public interface WiseLiComponent {

    Application application();

    Picasso picasso();

    Gson gson();

    OkHttpClient okHttpClient();
}
