package uk.ac.tees.mad.w9501736.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import uk.ac.tees.mad.w9501736.cache.GuestData;
import uk.ac.tees.mad.w9501736.di.scopes.WiseLiScope;

@Module
public class WiseLiSharedDataModule {

    static final String PREFS_DEFAULT = "nowdothis";

    @Provides
    @WiseLiScope
    SharedPreferences provideSharedPrefs(Application app) {
        return app.getSharedPreferences(PREFS_DEFAULT, Context.MODE_PRIVATE);
    }

    @Provides
    @WiseLiScope
    Gson provideGson() {
        return new Gson();
    }

    @WiseLiScope
    @Provides
    GuestData provideGuestData(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(GuestData.PREFS_NAME, Context.MODE_PRIVATE);

        return new GuestData(prefs);
    }

}
