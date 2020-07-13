package com.cleanup.todoc.service.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.service.model.Project;
import com.cleanup.todoc.service.model.Task;

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
                }
            });
        }
    };

    public static synchronized TodocDatabase getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TodocDatabase.class, "TodocDatabase.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();
}
