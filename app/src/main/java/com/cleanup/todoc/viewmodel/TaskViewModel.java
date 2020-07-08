package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.injection.Injection;
import com.cleanup.todoc.service.model.Task;
import com.cleanup.todoc.service.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        this.taskRepository = Injection.provideTaskRepository(application);
    }

    public void insert(final Task task) {
        this.taskRepository.insert(task);
    }

    public void update(final Task task) {
        this.taskRepository.update(task);
    }

    public void delete(final Task task) {
        this.taskRepository.delete(task);
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskRepository.getTasks();
    }
}
