package org.itstep.organizer;

import android.app.Application;

import androidx.room.Room;

import org.itstep.organizer.data.AppDatabase;
import org.itstep.organizer.data.NoteDao;
import org.itstep.organizer.data.StudentDao;


public class App extends Application {

    private AppDatabase database;
    private NoteDao noteDao;
    private StudentDao studentDao;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "organizer_db")
                .allowMainThreadQueries().build();
        noteDao = database.noteDao();
        studentDao = database.studentDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }


}
