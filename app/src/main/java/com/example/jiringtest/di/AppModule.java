package com.example.jiringtest.di;



import android.app.Application;
import android.content.Context;

import com.example.jiringtest.api.ApiService;
import com.example.jiringtest.repository.LoginRepository;
import com.example.jiringtest.repository.TodoRepository;
import com.example.jiringtest.ui.loginScreen.LoginViewModel;
import com.example.jiringtest.ui.todoScreen.TodoViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {



    @Provides
    @Singleton
    public ApiService provideApiService(OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                // Configure OkHttpClient as needed
                .build();
    }


    @Provides
    @Singleton
    public Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public LoginViewModel provideLoginViewModel(LoginRepository loginRepository, Context context) {
        return new LoginViewModel(loginRepository, context);
    }

    @Provides
    @Singleton
    public TodoViewModel provideTodoViewModel(TodoRepository todoRepository, Context context) {
        return new TodoViewModel(todoRepository, context);
    }
}
