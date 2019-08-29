package com.arinspect.practicalapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arinspect.practicalapplication.R;
import com.arinspect.practicalapplication.model.Rows;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

//for binding data with RecyclerView
public class RowsAdapter extends RecyclerView.Adapter<RowsAdapter.ViewHolder> {

    private ArrayList<Rows> rowsList;
    private Context context;

    public RowsAdapter(Context context, ArrayList<Rows> rows_list) {
        this.context = context;
        rowsList = rows_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(rowsList.get(position).getTitle());
        holder.tv_description.setText(rowsList.get(position).getDescription());
        Glide.with(context).load(rowsList.get(position).getImageHref()).into(holder.img_image); // Image load with url
    }

    @Override
    public int getItemCount() {
        return rowsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title, tv_description;
        private ImageView img_image;

        public ViewHolder(View view) {
            super(view);

            tv_title = view.findViewById(R.id.tv_title);
            tv_description = view.findViewById(R.id.tv_description);
            img_image = view.findViewById(R.id.img_image);
        }
    }
}