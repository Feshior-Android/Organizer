package org.itstep.organizer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.itstep.organizer.App;
import org.itstep.organizer.model.Note;
import java.util.List;


public class MainViewModel extends ViewModel {

    private final LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }

}
