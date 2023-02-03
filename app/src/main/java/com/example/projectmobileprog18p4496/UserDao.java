package com.example.projectmobileprog18p4496;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM users_info")
    void deleteAll();

    @Query("SELECT * from users_info")
    LiveData<List<User>> getAllUsers();

}
