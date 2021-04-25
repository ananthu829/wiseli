package uk.ac.tees.mad.w9501736.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import uk.ac.tees.mad.w9501736.App;
import uk.ac.tees.mad.w9501736.cache.GuestData;
import uk.ac.tees.mad.w9501736.data.WiseLiRepository;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.di.scopes.WiseLiScope;

import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class WiseLiModule {

    static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);

    final App app;

    @WiseLiScope
    @Provides
    WiseLiRepository provideWiseLiRepository(WiseLiApiClient wiseLiApiClient) {
        return new WiseLiRepository(wiseLiApiClient);
    }

    public WiseLiModule(App app) {
        this.app = app;
    }

    @Provides
    @WiseLiScope
    App provideApp() {
        return app;
    }

    @Provides
    @WiseLiScope
    Application provideApplication(App app) {
        return app;
    }

    @WiseLiScope
    @Provides
    GuestData provideGuestData() {
        final SharedPreferences prefs = app.getSharedPreferences(GuestData.PREFS_NAME, Context.MODE_PRIVATE);

        return new GuestData(prefs);
    }

    @Provides
    @WiseLiScope
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, SECONDS);
        client.setReadTimeout(10, SECONDS);
        client.setWriteTimeout(10, SECONDS);

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);

        return client;
    }

    @Provides
    @WiseLiScope
    Picasso providePicasso(OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .listener((picasso, uri, e) -> Log.e("Picasso", "Failed to load image: %s", e))
                .build();
    }


/*    @WiseLiScope
    @Provides
    FolioApiClient provideFolioApiClient(RemoteConfig remoteConfig, GuestData guestData,
                                         RetrofitHelper retrofitHelper) {
        return new FolioApiClient(remoteConfig, guestData, retrofitHelper);
    }

    @WiseLiScope
    @Provides
    FolioRepository provideFolioRepository(RemoteConfig remoteConfig, CacheControl cacheControl,
                                           FolioApiClient folioApiClient, AssemblyServiceApiClient assemblyServiceApiClient) {
        return new FolioRepository(remoteConfig, cacheControl, folioApiClient, assemblyServiceApiClient);
    }

    @Provides
    FolioViewModelFactory provideFolioViewModelFactory(FolioRepository folioRepository,
                                                       ShipConnectivityLiveData shipConnectivityLiveData,
                                                       FolioMetricsAnalytics folioMetricsAnalytics) {
        return new FolioViewModelFactory(folioRepository, shipConnectivityLiveData, folioMetricsAnalytics);
    }

    @Provides
    FolioAdapter provideFolioAdapter() {
        return new FolioAdapter();
    }

    @WiseLiScope
    @Provides
    FolioAnalytics provideFolioAnalytics(DclOnBoardAnalyticsHelper dclOnBoardAnalyticsHelper) {
        return new FolioAnalytics(dclOnBoardAnalyticsHelper);
    }

    @WiseLiScope
    @Provides
    FolioMetricsAnalytics provideFolioMetricsAnalytics(AppMetricsHelper appMetricsHelper) {
        return new FolioMetricsAnalytics(appMetricsHelper);
    }*/

}
