package com.example.jiringtest.repository;

import com.example.jiringtest.api.ApiService;
import com.example.jiringtest.model.LoginResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class LoginRepository {

    private ApiService apiService;

    @Inject
    public LoginRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Response<LoginResponse>> login(String userName) {
        return apiService.login(userName);
    }
}
