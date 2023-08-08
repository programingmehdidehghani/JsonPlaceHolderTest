package com.example.jiringtest.api;

import com.example.jiringtest.model.LoginResponse;
import com.example.jiringtest.model.TodoResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Single<Response<LoginResponse>> login(
            @Query("username") String username
    );

    @GET("todos")
    Single<List<TodoResponse>> todo(
            @Query("userId") int userId
    );
}
