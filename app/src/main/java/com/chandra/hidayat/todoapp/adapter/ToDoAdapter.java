package com.chandra.hidayat.todoapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chandra.hidayat.todoapp.R;
import com.chandra.hidayat.todoapp.model.ToDo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private static final String TAG = "ToDoAdapter";
    private ArrayList<ToDo> list = new ArrayList<>();
    private Context context;

    public ToDoAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ToDo> getList() {
        return list;
    }

    public void setList(ArrayList<ToDo> list) {
        this.list.addAll(list);
    }

    public void clearList(){
        final int listSize = getItemCount();
        list.clear();
        notifyItemRangeRemoved(0,listSize);
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_todo, viewGroup, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder toDoViewHolder, int i) {
        toDoViewHolder.tvTitle.setText(getList().get(i).getTitle());
        toDoViewHolder.tvDescription.setText(getList().get(i).getDescription());
        toDoViewHolder.tvDate.setText(getList().get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView tvTitle;
        @BindView(R.id.description)
        TextView tvDescription;
        @BindView(R.id.date)
        TextView tvDate;
        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
