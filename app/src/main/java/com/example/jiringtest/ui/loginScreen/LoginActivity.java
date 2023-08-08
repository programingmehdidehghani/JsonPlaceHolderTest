package com.example.jiringtest.ui.loginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jiringtest.databinding.ActivityLoginBinding;
import com.example.jiringtest.model.LoginResponse;
import com.example.jiringtest.ui.todoScreen.TodoActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Response;


@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Inject
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.btnLoginInActivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.etUserNameInLoginActivity.getText().toString().equals("")){
                    binding.progressInLoginActivity.setVisibility(View.VISIBLE);
                    loginViewModel.login(binding.etUserNameInLoginActivity.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "please Enter User Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginViewModel.getLoginResponse().observe(this, new Observer<Response<LoginResponse>>() {
            @Override
            public void onChanged(Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    binding.progressInLoginActivity.setVisibility(View.GONE);
                    if (response.body() != null){
                        LoginResponse loginResponse = response.body();
                        if (!loginResponse.isEmpty()) {
                            LoginResponse.LoginResponseItem loginResponseItem = loginResponse.get(0);
                            int id = loginResponseItem.getId();
                            Intent intent = new Intent(LoginActivity.this, TodoActivity.class);
                            intent.putExtra("userId",id);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "UserName is not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        loginViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                binding.progressInLoginActivity.setVisibility(View.VISIBLE);
            }
        });

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                binding.progressInLoginActivity.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
