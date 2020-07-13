package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.service.database.TodocDatabase;
import com.cleanup.todoc.service.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private static long PROJECT_ID = 2L;
    private static long PROJECT_UPDATED_ID = 2L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "TestProject", 0xFFFFFF);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TodocDatabase database;

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getProjectsWhenNoProjectInserted() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(projects.isEmpty());
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.database.projectDao().insert(PROJECT_DEMO);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(projects.get(0).getId() == PROJECT_DEMO.getId()
                && projects.get(0).getName().equals(PROJECT_DEMO.getName())
                && projects.get(0).getColor() == PROJECT_DEMO.getColor());
    }

    @Test
    public void insertAndUpdateProject() throws InterruptedException {
        this.database.projectDao().insert(PROJECT_DEMO);
        PROJECT_DEMO.setId(PROJECT_UPDATED_ID);
        this.database.projectDao().update(PROJECT_DEMO);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertEquals(PROJECT_UPDATED_ID, projects.get(0).getId());
    }

    @Test
    public void insertAndDeleteProject() throws InterruptedException {
        this.database.projectDao().insert(PROJECT_DEMO);
        this.database.projectDao().delete(PROJECT_DEMO);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(projects.isEmpty());
    }
}

