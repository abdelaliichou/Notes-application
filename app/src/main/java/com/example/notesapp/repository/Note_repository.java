package com.example.notesapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesapp.Dao.Note_Dao;
import com.example.notesapp.Model.Note_model;
import com.example.notesapp.Room_database.Notes_RoomDatabase;

import java.util.List;

public class Note_repository {

    private Note_Dao Note_Dao_iterface;
    private LiveData<List<Note_model>> AllElements , HightToLow , LowToHigh ;

    public Note_repository(Application application) {
        Notes_RoomDatabase database = Notes_RoomDatabase.getDatabase(application);
        Note_Dao_iterface = database.ModelDAO();
        AllElements = Note_Dao_iterface.get_All();
        HightToLow = Note_Dao_iterface.HighToLow();
        LowToHigh = Note_Dao_iterface.LowToHigh();
    }

    public LiveData<List<Note_model>> GetAllElements() {
        return AllElements ;
    }
    public LiveData<List<Note_model>> GetAllElementsHighToLow() {
        return HightToLow ;
    }
    public LiveData<List<Note_model>> GetAllElementsLowToHigh() {
        return LowToHigh ;
    }

    public void Add(Note_model element){
        Notes_RoomDatabase.RoomDatabaseExecutor.execute(() -> {
            Note_Dao_iterface.add_Note(element);
        });
    }

    public LiveData<Note_model> getElementByID(int id){
        return Note_Dao_iterface.getNote_byID(id);
    }

    public void update(Note_model model){
        Notes_RoomDatabase.RoomDatabaseExecutor.execute(() -> {
            Note_Dao_iterface.update_Note(model);
        });
    }

    public void Delete(Note_model model){
        Notes_RoomDatabase.RoomDatabaseExecutor.execute(() -> {
            Note_Dao_iterface.delete_Note(model);
        });
    }
}
