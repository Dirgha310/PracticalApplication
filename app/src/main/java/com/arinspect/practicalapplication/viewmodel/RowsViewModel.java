package com.arinspect.practicalapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.arinspect.practicalapplication.model.RowsList;
import com.arinspect.practicalapplication.network.RowsRepository;

public class RowsViewModel  extends ViewModel {

    private MutableLiveData<RowsList> mutableLiveData;

    public void init(Context context){
        if (mutableLiveData != null){
            return;
        }

        //get data from repository
        mutableLiveData = RowsRepository.getInstance(context).getRows();
    }

    //send data to main UI
    public LiveData<RowsList> getNewsRepository() {
        return mutableLiveData;
    }
}