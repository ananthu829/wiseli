package uk.ac.tees.mad.w9501736.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BaseViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> progressDialog = new MutableLiveData<>();
    MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getProgressDialog() {
        return progressDialog;
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }
}
