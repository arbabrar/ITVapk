package policiadnfr.gob.itvremolques.Data.Views.Activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReservaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReservaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}