package com.arinspect.practicalapplication.network;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.arinspect.practicalapplication.model.Rows;
import com.arinspect.practicalapplication.model.RowsList;
import com.arinspect.practicalapplication.model.local.RowsDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RowsRepository {

    private static RowsRepository rowsRepository;

    private RowsDatabase rDb;
    private SharedPreferences.Editor rEditor;
    private SharedPreferences rPref;
    private Context rContext;

    private RequestAPI rowsApi;

    public static RowsRepository getInstance(Context context) {
        if (rowsRepository == null) {
            rowsRepository = new RowsRepository(context);
        }
        return rowsRepository;
    }

    public RowsRepository(Context context) {
        rContext = context;
        rDb = Room.databaseBuilder(context,  RowsDatabase.class, "rows").build();
        rPref = context.getSharedPreferences("MyPref", 0);
        rEditor = rPref.edit();
        rowsApi = RetrofitService.cteateService(RequestAPI.class);
    }

    //fetch data
    public MutableLiveData<RowsList> getRows() {
        final MutableLiveData<RowsList> rowsData = new MutableLiveData<>();
        rowsApi.getData().enqueue(new Callback<RowsList>() {

            //if online
            @Override
            public void onResponse(Call<RowsList> call,   Response<RowsList> response) {
                if (response.isSuccessful()) {
                    rowsData.setValue(response.body());
                    deleteTask(response.body().getList(),response.body().getTitle());
                }
            }

            //if offline or get any error from REST api
            @Override
            public void onFailure(Call<RowsList> call, Throwable t) {

               getListTasks().observe((LifecycleOwner) rContext, new Observer<List<Rows>>() {
                   @Override
                   public void onChanged(@Nullable List<Rows> rows) {
                       if(rows.size() > 0) {
                           RowsList rowsList = new RowsList();
                           rowsList.setTitle(rPref.getString("title", null));
                           rowsList.setRowsList(rows);
                           rowsData.setValue(rowsList);
                       } else {
                           rowsData.setValue(null);
                       }
                   }
               });
            }
        });
        return rowsData;
    }

    //delete all data from table
    void deleteTask(final List<Rows> rowsList,final String title){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rDb.rowsDao().deleteAllRows();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                insertTask(rowsList,title);
            }
        }.execute();
    }

    //insert data into table
     void insertTask(final List<Rows> rowsList,final String title) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (int i = 0; i < rowsList.size(); i++) {
                    rDb.rowsDao().insertRows(rowsList.get(i));
                }
                rEditor.putString("title",title);
                rEditor.commit();
                return null;
            }
        }.execute();
    }

    //fetch all data from database
    LiveData<List<Rows>> getListTasks(){
        return rDb.rowsDao().getAllRows();
    }
}
