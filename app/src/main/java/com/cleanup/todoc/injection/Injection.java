package com.cleanup.todoc.injection;

import android.app.Application;

import com.cleanup.todoc.service.database.TodocDatabase;
import com.cleanup.todoc.service.repository.TaskRepository;

public abstract class Injection {
    public static TaskRepository provideTaskRepository(Application application) {
        TodocDatabase database = TodocDatabase.getInstance(application);
        return new TaskRepository(database.taskDao());
    }
}
