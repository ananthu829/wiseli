package uk.ac.tees.mad.w9501736.di.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import uk.ac.tees.mad.w9501736.App;
import uk.ac.tees.mad.w9501736.di.scopes.WiseLiScope;

@Module
public class WiseLiModule {

    final App app;

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
