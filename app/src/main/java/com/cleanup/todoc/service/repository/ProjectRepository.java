package com.cleanup.todoc.service.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.service.database.ProjectDao;
import com.cleanup.todoc.service.model.Project;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProjectRepository {

    private ProjectDao projectDao;
    private Executor executor;

    public ProjectRepository(@NonNull ProjectDao projectDao) {
        this.projectDao = projectDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(final Project project) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.insert(project);
            }
        });
    }

    public void update(final Project project) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.update(project);
            }
        });
    }

    public void delete(final Project project) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.delete(project);
            }
        });
    }

    public LiveData<List<Project>> getAllProjects() {
        return this.projectDao.getAllProjects();
    }
}
