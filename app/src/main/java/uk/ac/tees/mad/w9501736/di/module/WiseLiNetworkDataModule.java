package uk.ac.tees.mad.w9501736.di.module;

import android.app.Application;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import uk.ac.tees.mad.w9501736.di.scopes.WiseLiScope;

import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class WiseLiNetworkDataModule {

    static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);

    @Provides
    @WiseLiScope
    OkHttpClient provideOkHttpClient(Application app) {
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
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .listener((picasso, uri, e) -> Log.e("Picasso", "Failed to load image: %s", e))
                .build();
    }

}
