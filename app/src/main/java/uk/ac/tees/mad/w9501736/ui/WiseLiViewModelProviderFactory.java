package uk.ac.tees.mad.w9501736.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory class to create ViewModel which has parameter/s constructor view models.
 *
 * @author bchougul - 9/20/2018.
 */

public class WiseLiViewModelProviderFactory<V extends ViewModel> implements ViewModelProvider.Factory {

    private final V model;

    public WiseLiViewModelProviderFactory(V model) {
        this.model = model;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(model.getClass())) {
            return (T) model;
        }
        throw new IllegalArgumentException("Unknown class " + modelClass.getSimpleName());
    }
}
