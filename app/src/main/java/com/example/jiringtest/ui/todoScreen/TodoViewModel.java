package com.example.jiringtest.ui.todoScreen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jiringtest.model.LoginResponse;
import com.example.jiringtest.model.TodoResponse;
import com.example.jiringtest.repository.LoginRepository;
import com.example.jiringtest.repository.TodoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

@HiltViewModel
public class TodoViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    private TodoRepository todoRepository;

    private MutableLiveData<List<TodoResponse>> loginResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final Context context;

    @Inject
    public TodoViewModel(TodoRepository todoRepository, Context context) {
        this.compositeDisposable = new CompositeDisposable();
        this.todoRepository = todoRepository;
        this.context = context;
    }


    public LiveData<List<TodoResponse>> getLoginResponse() {
        return loginResponseLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public void todo(int userId) {
        if (isInternetConnected()) {
            isLoadingLiveData.setValue(true);

            compositeDisposable.add(todoRepository.todo(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<TodoResponse>>() {
                        @Override
                        public void onSuccess(List<TodoResponse> response) {
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
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
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