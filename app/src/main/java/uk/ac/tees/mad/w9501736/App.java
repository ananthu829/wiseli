package uk.ac.tees.mad.w9501736;

import android.app.Application;
import android.content.Context;

import uk.ac.tees.mad.w9501736.cache.GuestData;
import uk.ac.tees.mad.w9501736.di.components.WiseLiComponent;
import uk.ac.tees.mad.w9501736.di.components.WiseLiComponentProvider;


/**
 * The application level class
 */
public class App extends Application implements WiseLiComponentProvider {

    // Reference to the application graph that is used across the whole app
    private WiseLiComponent wiseLiComponent;

    public static WiseLiComponent getComponent(Context context) {
        return ((App) context.getApplicationContext()).wiseLiComponent;
    }

    /**
     * On application created
     */
    @Override
    public void onCreate() {
        super.onCreate();

        GuestData.init(this);


    }

    @Override
    public WiseLiComponent getWiseLiComponent() {
        return wiseLiComponent;
    }
}
