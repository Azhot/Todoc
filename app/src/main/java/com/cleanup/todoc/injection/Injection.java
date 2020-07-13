package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.service.database.TodocDatabase;
import com.cleanup.todoc.service.repository.ProjectRepository;
import com.cleanup.todoc.service.repository.TaskRepository;

public abstract class Injection {

    public static TaskRepository provideTaskRepository(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskRepository(database.taskDao());
    }

    public static ProjectRepository provideProjectRepository(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectRepository(database.projectDao());
    }
}
