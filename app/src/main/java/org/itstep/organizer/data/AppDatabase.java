package org.itstep.organizer.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.itstep.organizer.model.Note;
import org.itstep.organizer.model.Student;

@Database(entities = {Note.class, Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract StudentDao studentDao();
}
