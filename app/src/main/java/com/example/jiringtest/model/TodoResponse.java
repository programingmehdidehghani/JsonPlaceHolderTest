package com.example.jiringtest.model;

import java.util.ArrayList;

public class TodoResponse extends ArrayList<TodoResponse.TItem> {

    public static class TItem {
        private int userId;
        private int id;
        private String title;
        private boolean completed;

        public TItem(int userId, int id, String title, boolean completed) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.completed = completed;
        }

        public int getUserId() {
            return userId;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return completed;
        }
    }
}