package com.example.notesapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note_table")
public class Note_model {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "note_title")
    String noteTitle;

    @ColumnInfo(name = "note_subTitle")
    String noteSubTitle;

    @ColumnInfo(name = "note_date")
    String noteDate;

    @ColumnInfo(name = "note_priority")
    String notePriority;

    @ColumnInfo(name = "note_body")
    String noteBody;

    public Note_model(String noteTitle, String noteSubTitle, String noteDate, String notePriority, String noteBody) {
        this.noteTitle = noteTitle;
        this.noteSubTitle = noteSubTitle;
        this.noteDate = noteDate;
        this.notePriority = notePriority;
        this.noteBody = noteBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteSubTitle() {
        return noteSubTitle;
    }

    public void setNoteSubTitle(String noteSubTitle) {
        this.noteSubTitle = noteSubTitle;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNotePriority() {
        return notePriority;
    }

    public void setNotePriority(String notePriority) {
        this.notePriority = notePriority;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }
}
