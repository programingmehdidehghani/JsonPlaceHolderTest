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

    private MutableLiveData<Response<TodoResponse>> todoResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    private Context context;

    @Inject
    public TodoViewModel(TodoRepository todoRepository, Context context) {
        this.todoRepository = todoRepository;
        this.context = context;
    }


    public LiveData<Response<TodoResponse>> getTodoResponse() {
        return todoResponseLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public void clearData() {
        isLoadingLiveData.setValue(false);
        todoResponseLiveData.setValue(null);
        compositeDisposable.clear();
    }

    public void todo(int userId) {
        if (isInternetConnected()) {
            isLoadingLiveData.setValue(true);
            this.compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(todoRepository.todo(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Response<TodoResponse>>() {
                        @Override
                        public void onSuccess(Response<TodoResponse> response) {
                            isLoadingLiveData.setValue(false);
                            todoResponseLiveData.setValue(response);
                            Log.i("error","rx call iis "+ response.message());
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