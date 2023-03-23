package java.com.noteapp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    NoteRepository noteRepository;
    LiveData<List<Note>> allNotes;
    public NoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application.getApplicationContext());
        allNotes = noteRepository.getAllNotes();
    }
    public void insert(Note note){
        noteRepository.insert(note);
    }
    public void update(Note note){
        noteRepository.update(note);
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }
    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
