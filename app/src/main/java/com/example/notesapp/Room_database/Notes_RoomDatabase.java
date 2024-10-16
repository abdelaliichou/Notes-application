package com.example.notesapp.Room_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notesapp.Dao.Note_Dao;
import com.example.notesapp.Model.Note_model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note_model.class}, version = 1, exportSchema = false)
public abstract class Notes_RoomDatabase extends RoomDatabase {

    public abstract Note_Dao ModelDAO();

    private static volatile Notes_RoomDatabase DATABASE_INSTANCE;

    public static final ExecutorService RoomDatabaseExecutor = Executors.newFixedThreadPool(4);

    public static Notes_RoomDatabase getDatabase(final Context context) {
        if (DATABASE_INSTANCE == null) {
            synchronized (Notes_RoomDatabase.class) {
                if (DATABASE_INSTANCE == null) {
                    DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Notes_RoomDatabase.class,
                                    "Note_table")
                            .addCallback(RoomDatabaseCallback)
                            .build();
                }
            }
        }
        return DATABASE_INSTANCE;
    }

    private static final RoomDatabase.Callback RoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    RoomDatabaseExecutor.execute(() -> {

                        Note_Dao ModelDao = DATABASE_INSTANCE.ModelDAO();
                        ModelDao.delete_All();

                        Note_model model = new Note_model("Ali ichou", "Esi student",
                                "20:00 am","1","hellllllllllllo"); // initial element
                        ModelDao.add_Note(model);

                    });
                }
            };
}
