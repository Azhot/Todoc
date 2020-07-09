package com.cleanup.todoc.service.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.service.model.Project;
import com.cleanup.todoc.service.model.Task;

import java.util.Date;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    private static TodocDatabase INSTANCE;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    ProjectDao projectDao = INSTANCE.projectDao();
                    projectDao.insert(new Project("Projet Tartampion", 0xFFEADAD1));
                    projectDao.insert(new Project("Projet Lucidia", 0xFFB4CDBA));
                    projectDao.insert(new Project("Projet Circus", 0xFFA3CED2));

                    TaskDao taskDao = INSTANCE.taskDao();
                    taskDao.insert(new Task(1L, "Ajouter un header sur le site", new Date(1L).getTime()));
                    taskDao.insert(new Task(2L, "Modifier la couleur des textes", new Date(2L).getTime()));
                    taskDao.insert(new Task(2L, "Appeler le client", new Date(3L).getTime()));
                    taskDao.insert(new Task(1L, "Intégrer Google Analytics", new Date(4L).getTime()));
                    taskDao.insert(new Task(3L, "Ajouter un header sur le site", new Date(5L).getTime()));
                }
            });
        }
    };

    public static synchronized TodocDatabase getInstance(@NonNull Application application) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(application.getApplicationContext(), TodocDatabase.class, "TodocDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();
}
