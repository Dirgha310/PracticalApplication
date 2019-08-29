package com.arinspect.practicalapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arinspect.practicalapplication.adapter.RowsAdapter;
import com.arinspect.practicalapplication.model.Rows;
import com.arinspect.practicalapplication.model.RowsList;
import com.arinspect.practicalapplication.viewmodel.RowsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lin_root)
    SwipeRefreshLayout lin_root;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    ArrayList<Rows> rowsList = new ArrayList<>();

    RowsAdapter rowsAdapter;
    RowsViewModel rowsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rowsViewModel = ViewModelProviders.of(this).get(RowsViewModel.class);
        rowsViewModel.init(MainActivity.this);

        //for refresh data
        lin_root.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRows();
            }
        });

        setRows();
        setupRecyclerView();
        setActionBar("");
    }

    //get data from repository
    private void setRows() {
        rowsViewModel.getNewsRepository().observe(MainActivity.this, new Observer<RowsList>() {
            @Override
            public void onChanged(@Nullable RowsList rowsResponse) {
                lin_root.setRefreshing(false);
                if (rowsResponse == null)
                    Snackbar.make(lin_root, "No data available", Snackbar.LENGTH_SHORT).show();
                else {
                    rowsList.clear();
                    List<Rows> rows_list = rowsResponse.getList();
                    rowsList.addAll(rows_list);
                    rowsAdapter.notifyDataSetChanged();
                    setActionBar(rowsResponse.getTitle());
                }
            }
        });
    }

    //bind data with RecyclerView
    private void setupRecyclerView() {
        if (rowsAdapter == null) {
            rowsAdapter = new RowsAdapter(MainActivity.this, rowsList);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(rowsAdapter);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setNestedScrollingEnabled(true);
        } else {
            rowsAdapter.notifyDataSetChanged();
        }
    }

    //set the title in the ActionBar
    public void setActionBar(String heading) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(heading);
        actionBar.show();
    }
}
