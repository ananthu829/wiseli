package uk.ac.tees.mad.w9501736.ui.viewModel.splashScreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import uk.ac.tees.mad.w9501736.data.WiseLiRepository;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.ui.viewModel.BaseViewModel;

public class SplashScreenFragmentViewModel extends BaseViewModel {

    private WiseLiRepository wiseLiRepository;
    private MutableLiveData<Resource<WiseLiUser>> wiseLiUserMutableLiveData = new MutableLiveData<>();
    private LiveData<Resource<WiseLiUser>> wiseLiUserLiveData;

    public SplashScreenFragmentViewModel(@NonNull Application application) {
        super(application);
        wiseLiRepository = WiseLiRepository.getInstance(application);
    }

}
