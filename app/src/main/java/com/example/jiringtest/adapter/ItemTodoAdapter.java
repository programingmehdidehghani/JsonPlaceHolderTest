package com.example.jiringtest.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jiringtest.databinding.ItemTodoListBinding;
import com.example.jiringtest.model.TodoResponse;

import java.util.ArrayList;
import java.util.List;

public class ItemTodoAdapter extends RecyclerView.Adapter<ItemTodoAdapter.ItemTodoViewHolder> {

    private ArrayList<TodoResponse.TItem> itemArrayList = new ArrayList<>();


    @NonNull
    @Override
    public ItemTodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodoListBinding binding = ItemTodoListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemTodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTodoViewHolder holder, int position) {
        holder.bind(itemArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<TodoResponse.TItem> list) {
        this.itemArrayList.clear();
        this.itemArrayList.addAll(list);
        notifyDataSetChanged();
    }

    static class ItemTodoViewHolder extends RecyclerView.ViewHolder {

        private ItemTodoListBinding binding;

        public ItemTodoViewHolder(ItemTodoListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(TodoResponse.TItem item) {
            binding.idInItemTodo.setText(String.valueOf(item.getId()));
            binding.titleInItemTodo.setText(item.getTitle());
            if (item.isCompleted()){
                binding.completeInItemTodo.setText("ture");
            }else {
                binding.completeInItemTodo.setText("false");
            }
        }
    }

}


