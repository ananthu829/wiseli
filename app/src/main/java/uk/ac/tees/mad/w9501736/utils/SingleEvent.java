package uk.ac.tees.mad.w9501736.utils;

/**
 * Wrapper class to add the capability to know
 * when a data object has been consumed.
 *
 * @param <T> The data object type.
 * @author daniel.leonett
 */
public class SingleEvent<T> {

    private T content;
    private boolean eventHandled;

    public SingleEvent(T content) {
        this.content = content;
        this.eventHandled = false;
    }

    public T getContentIfNotHandled() {
        if (eventHandled) {
            return null;
        } else {
            eventHandled = true;
            return content;
        }
    }

    public T peekContent() {
        return content;
    }

    public boolean hasBeenHandled() {
        return eventHandled;
    }

}
