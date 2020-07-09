package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.injection.Injection;
import com.cleanup.todoc.service.model.Project;
import com.cleanup.todoc.service.repository.ProjectRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository projectRepository;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        this.projectRepository = Injection.provideProjectRepository(application);
    }

    public void insert(Project project) {
        this.projectRepository.insert(project);
    }

    public void update(Project project) {
        this.projectRepository.update(project);
    }

    public void delete(Project project) {
        this.projectRepository.delete(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        return this.projectRepository.getAllProjects();
    }
}
