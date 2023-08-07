package com.example.jiringtest.ui.loginScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jiringtest.api.ApiService;
import com.example.jiringtest.model.LoginResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private ApiService apiService;
    private CompositeDisposable compositeDisposable;

    private MutableLiveData<Response<LoginResponse>> loginResponseLiveData;
    private MutableLiveData<Boolean> isLoadingLiveData;
    private MutableLiveData<String> errorMessageLiveData;

    @Inject
    public LoginViewModel(ApiService apiService) {
        this.apiService = apiService;
        this.compositeDisposable = new CompositeDisposable();

        this.loginResponseLiveData = new MutableLiveData<>();
        this.isLoadingLiveData = new MutableLiveData<>();
        this.errorMessageLiveData = new MutableLiveData<>();
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
        isLoadingLiveData.setValue(true);

        compositeDisposable.add(apiService.login(userName)
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
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}