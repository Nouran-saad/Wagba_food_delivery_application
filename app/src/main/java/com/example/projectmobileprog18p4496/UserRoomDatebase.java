package com.example.projectmobileprog18p4496;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatebase extends RoomDatabase {

    public abstract UserDao userDao ();
    private static UserRoomDatebase INSTANCE;

    public synchronized static UserRoomDatebase getDatabase(final Context context) {

            if (INSTANCE == null) {
        synchronized (UserRoomDatebase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                UserRoomDatebase.class, "user_database")
                        // Wipes and rebuilds instead of migrating
                        // if no Migration object.
                        // Migration is not part of this practical.
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
    }
        return INSTANCE;
}
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }
            };
}
