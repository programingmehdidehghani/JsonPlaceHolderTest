package com.example.jiringtest.ui.loginScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jiringtest.api.ApiService;
import com.example.jiringtest.model.LoginResponse;
import com.example.jiringtest.repository.LoginRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    private LoginRepository loginRepository;

    private MutableLiveData<Response<LoginResponse>> loginResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    @SuppressLint("StaticFieldLeak")
    private Context context;

    @Inject
    public LoginViewModel(LoginRepository loginRepository, Context context) {
        this.loginRepository = loginRepository;
        this.context = context;
    }


    public LiveData<Response<LoginResponse>> getLoginResponse() {
        return loginResponseLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }



    public void login(String userName) {
        if (isInternetConnected()) {
            isLoadingLiveData.setValue(true);
            this.compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(loginRepository.login(userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Response<LoginResponse>>() {
                        @Override
                        public void onSuccess(Response<LoginResponse> response) {
                            isLoadingLiveData.setValue(false);
                            loginResponseLiveData.setValue(response);
                        }

                        @Override
                        public void onError(Throwable e) {
                            isLoadingLiveData.setValue(false);
                            errorMessageLiveData.setValue(e.getMessage());
                            Log.i("response","error is " + e.getMessage());}
                    }));
        } else {
            errorMessageLiveData.setValue("No internet connection");
        }
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}