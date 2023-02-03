package com.example.projectmobileprog18p4496;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_info")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_name")
    private String UserName;

    @ColumnInfo(name = "gender")
    private String Gender;

    @ColumnInfo(name = "birth_date")
    private String BirthDate;

    public User (){

    }
    public User(@NonNull String userName, String gender, String birthDate) {
        this.UserName = userName;
        this.Gender = gender;
        this.BirthDate = birthDate;
    }

    @NonNull
    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(@NonNull String userName) {
        this.UserName = userName;
    }

    public String getGender() {
        return this.Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getBirthDate() {
        return this.BirthDate;
    }

    public void setBirthDate(String birthDate) {
        this.BirthDate = birthDate;
    }
}
