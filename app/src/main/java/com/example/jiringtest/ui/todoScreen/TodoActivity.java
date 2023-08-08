package com.example.jiringtest.ui.todoScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.jiringtest.R;
import com.example.jiringtest.databinding.ActivityLoginBinding;
import com.example.jiringtest.databinding.ActivityTodoBinding;
import com.example.jiringtest.ui.loginScreen.LoginViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class TodoActivity extends AppCompatActivity {

    private ActivityTodoBinding binding;

    @Inject
    TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}