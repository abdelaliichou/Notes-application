package com.example.notesapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.Model.Note_model;
import com.example.notesapp.repository.Note_repository;

import java.util.List;

public class Notes_ViewModel extends AndroidViewModel {

    public static Note_repository repository ;
    public final LiveData<List<Note_model>> allElements , Hightolow , Lowtohigh;

    public Notes_ViewModel(@NonNull Application application) {
        super(application);
        repository = new Note_repository(application);
        allElements = repository.GetAllElements();
        Hightolow = repository.GetAllElementsHighToLow();
        Lowtohigh = repository.GetAllElementsLowToHigh();
    }

    public LiveData<List<Note_model>> get_All () {
        return allElements ;
    }
    public LiveData<List<Note_model>> get_All_High_To_Low () {
        return Hightolow ;
    }
    public LiveData<List<Note_model>> get_All_Low_To_High () {
        return Lowtohigh ;
    }
    public static void add_Note(Note_model element){
        repository.Add(element);
    }

    public LiveData<Note_model> getNote_byID(int id){ return repository.getElementByID(id);}
    public static void update_Note(Note_model element){ repository.update(element);}
    public static void delete_Note(Note_model element) { repository.Delete(element);}

}
