package uk.ac.tees.mad.w9501736.utils;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * Custom {@link Observer} meant to work together
 * with {@link SingleEvent}.
 * <p>
 * It notifies a change on the event only if it
 * hasn't been previously handled.
 *
 * @param <T> The data object type.
 * @author daniel.leonett
 */
public abstract class SingleEventObserver<T> implements Observer<SingleEvent<T>> {

    @Override
    public void onChanged(@Nullable SingleEvent<T> event) {
        if (event != null) {
            T content = event.getContentIfNotHandled();
            if (content != null) {
                onUnhandledEvent(content);
            }
        }
    }

    public abstract void onUnhandledEvent(T content);

}
