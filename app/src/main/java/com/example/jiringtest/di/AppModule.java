package com.example.jiringtest.di;



import android.app.Application;
import android.content.Context;

import com.example.jiringtest.api.ApiService;
import com.example.jiringtest.repository.LoginRepository;
import com.example.jiringtest.ui.loginScreen.LoginViewModel;

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

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";


    @Provides
    @Singleton
    public ApiService provideApiService(OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
}
