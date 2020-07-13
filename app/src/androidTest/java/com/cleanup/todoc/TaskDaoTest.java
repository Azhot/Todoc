package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.service.database.TodocDatabase;
import com.cleanup.todoc.service.model.Project;
import com.cleanup.todoc.service.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private static long TASK_ID = 2L;
    private static long TASK_UPDATED_ID = 2L;
    private static long PROJECT_ID = 2L;
    private static Project PROJECT_TEST = new Project(PROJECT_ID, "TestProject1", 0xFFFFFF);
    private static Task TASK_DEMO = new Task(TASK_ID, PROJECT_TEST.getId(), "TestTask", new Date().getTime());
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TodocDatabase database;

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

        this.database.projectDao().insert(PROJECT_TEST);
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        this.database.taskDao().insert(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.get(0).getId() == TASK_DEMO.getId()
                && tasks.get(0).getName().equals(TASK_DEMO.getName())
                && tasks.get(0).getProjectId() == TASK_DEMO.getProjectId()
                && tasks.get(0).getCreationTimestamp() == TASK_DEMO.getCreationTimestamp());
    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        this.database.taskDao().insert(TASK_DEMO);
        TASK_DEMO.setId(TASK_UPDATED_ID);
        this.database.taskDao().update(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertEquals(TASK_UPDATED_ID, tasks.get(0).getId());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        this.database.taskDao().insert(TASK_DEMO);
        this.database.taskDao().delete(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
}