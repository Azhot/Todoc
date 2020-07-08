package com.cleanup.todoc.service.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.service.database.TaskDao;
import com.cleanup.todoc.service.model.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {

    private TaskDao taskDao;
    private Executor executor;

    public TaskRepository(@NonNull TaskDao taskDao) {
        this.taskDao = taskDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(final Task task) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
            }
        });
    }

    public void update(final Task task) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.update(task);
            }
        });
    }

    public void delete(final Task task) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.delete(task);
            }
        });
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }
}
