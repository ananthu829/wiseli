package uz.nuper.driver.database;

public interface DatabaseCallBack<T> {
    public abstract void onSuccess(T result);
}
