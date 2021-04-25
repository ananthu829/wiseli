package uk.ac.tees.mad.w9501736.ui.viewModel.RegisterPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uk.ac.tees.mad.w9501736.data.WiseLiRepository;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.utils.Resource;

public class RegisterFragmentViewModel extends AndroidViewModel {

    private WiseLiRepository wiseLiRepository;

    public RegisterFragmentViewModel(@NonNull Application application) {
        super(application);
        wiseLiRepository = WiseLiRepository.getInstance(application);
    }

    /**
     * Register API call request.
     */
    public LiveData<Resource<WiseLiUser>> getRegisterUserData(WiseLiUser wiseLiUser) {
        return wiseLiRepository.registerUser(wiseLiUser);
    }

}
