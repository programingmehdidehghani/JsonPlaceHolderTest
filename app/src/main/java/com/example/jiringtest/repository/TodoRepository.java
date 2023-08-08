package com.example.jiringtest.repository;

import com.example.jiringtest.api.ApiService;
import com.example.jiringtest.model.LoginResponse;
import com.example.jiringtest.model.TodoResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class TodoRepository {

    private ApiService apiService;

    @Inject
    public TodoRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<TodoResponse>> todo(int userId) {
        return apiService.todo(userId);
    }
}
