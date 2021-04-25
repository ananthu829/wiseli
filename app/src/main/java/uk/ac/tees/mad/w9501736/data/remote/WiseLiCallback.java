/* (c) Disney. All rights reserved. */
package uk.ac.tees.mad.w9501736.data.remote;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Custom implementation of the {@link Callback} that checks if the
 * response was successful and re-directs to specific methods so
 * the handling of the response is better made by the caller.
 * <p>
 * This callback can be passed to the {@link Call#enqueue(Callback)}
 * method directly instead of the normal Retrofit callback.
 *
 * @param <T> Successful response body type.
 */
public abstract class WiseLiCallback<T> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            boolean fromCache = (response.raw().networkResponse() == null);

            onResponseSuccess(response.body(), fromCache);
        } else {
            onResponseError(response.code());
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onFailure(new Exception(t));
    }

    /**
     * Invoked for a received HTTP successful response.
     */
    public abstract void onResponseSuccess(T t, boolean fromCache);

    /**
     * Invoked when an error response is received, returning the error code
     * sent by the service.
     */
    public abstract void onResponseError(int errorCode);

    /**
     * Invoked when an unknown error occurred talking to the server.
     */
    public abstract void onFailure(Exception e);
}
