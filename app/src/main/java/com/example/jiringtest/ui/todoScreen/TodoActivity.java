package com.example.jiringtest.ui.todoScreen;

import static com.example.jiringtest.model.TodoResponse.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jiringtest.adapter.ItemTodoAdapter;
import com.example.jiringtest.databinding.ActivityTodoBinding;
import com.example.jiringtest.model.TodoResponse;
import com.example.jiringtest.ui.loginScreen.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Response;


@AndroidEntryPoint
public class TodoActivity extends AppCompatActivity {

    private ActivityTodoBinding binding;

    @Inject
    TodoViewModel todoViewModel;

    private ItemTodoAdapter itemTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
        todoViewModel.todo(userId);

        todoViewModel.getTodoResponse().observe(this, new Observer<Response<TodoResponse>>() {
            @Override
            public void onChanged(Response<TodoResponse> response) {
                binding.progressInTodoActivity.setVisibility(View.GONE);
                if (response != null && response.isSuccessful()) {
                    TodoResponse todoResponse = response.body();
                    if (todoResponse != null) {
                        itemTodoAdapter = new ItemTodoAdapter();
                        binding.rvTodoInTodoActivity.setLayoutManager(new LinearLayoutManager(TodoActivity.this));
                        binding.rvTodoInTodoActivity.setAdapter(itemTodoAdapter);
                        List<TItem> todoList = new ArrayList<>(todoResponse);
                        itemTodoAdapter.updateList(todoList);
                    }
                }
            }
        });

        todoViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                binding.progressInTodoActivity.setVisibility(View.VISIBLE);
                Log.i("fix error","loading call is  ");
            }
        });

        todoViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                binding.progressInTodoActivity.setVisibility(View.GONE);
                Toast.makeText(TodoActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}