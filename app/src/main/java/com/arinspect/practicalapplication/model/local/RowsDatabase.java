package com.arinspect.practicalapplication.model.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.arinspect.practicalapplication.model.Rows;

//use to create database with Room
@Database(entities = {Rows.class}, version = 1, exportSchema = false)
public abstract class RowsDatabase extends RoomDatabase {

    public abstract RowsDao rowsDao();
}
