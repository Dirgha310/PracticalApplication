package com.arinspect.practicalapplication.model.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.arinspect.practicalapplication.model.Rows;

import java.util.List;

//use to access database
@Dao
public interface RowsDao {

    @Query("SELECT * FROM rows")
    LiveData<List<Rows>> getAllRows();

    @Insert
    void insertRows(Rows rows);

    @Query("DELETE FROM rows")
    void deleteAllRows();
}
