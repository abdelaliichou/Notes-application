package com.example.notesapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesapp.Model.Note_model;

import java.util.List;

@Dao
public interface Note_Dao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add_Note(Note_model element);

    @Query("DELETE FROM Note_table")
    void delete_All();

    @Query("SELECT * FROM Note_table ORDER BY note_priority DESC")
    LiveData<List<Note_model>> HighToLow();


    @Query("SELECT * FROM Note_table ORDER BY note_priority ASC")
    LiveData<List<Note_model>> LowToHigh();


    @Query("SELECT * FROM Note_table ")
    LiveData<List<Note_model>> get_All ();


    @Query("SELECT * FROM Note_table WHERE Note_table.id == :id")
    LiveData<Note_model> getNote_byID(int id);

    @Update
    void update_Note(Note_model element);

    @Delete
    void delete_Note(Note_model element);
}
