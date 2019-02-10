package com.chandra.hidayat.todoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chandra.hidayat.todoapp.R;
import com.chandra.hidayat.todoapp.adapter.ToDoAdapter;
import com.chandra.hidayat.todoapp.adapter.ToDoClickSupport;
import com.chandra.hidayat.todoapp.model.ToDo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    @BindView(R.id.card_no_data)
    CardView cardNoData;
    @BindView(R.id.fab_create)
    FloatingActionButton fabCreate;
    private ArrayList<ToDo> list;
    private ToDoAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter = new ToDoAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        reference = FirebaseDatabase.getInstance().getReference().child("list");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ToDo toDo = null;
                clearRecyclerList();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    toDo = data.getValue(ToDo.class);
                    toDo.setKey(data.getKey());
                    if (toDo != null) {
                        list.add(toDo);
                    }
                }
                if (toDo == null) {
                    clearRecyclerList();
                    recyclerView.removeAllViewsInLayout();
                    recyclerView.setAdapter(adapter);
                } else {
                    cardNoData.setVisibility(View.INVISIBLE);
                    adapter.setList(list);
                    recyclerView.setAdapter(adapter);
                    ToDoClickSupport.addTo(recyclerView).setmOnItemClickListener(new ToDoClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            Intent updateIntent = new Intent(MainActivity.this,UpdateActivity.class);
                            ToDo toDoUpdate = list.get(position);
                            updateIntent.putExtra(UpdateActivity.EXTRA_TODO,toDoUpdate);
                            startActivity(updateIntent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(MainActivity.this,CreateActivity.class);
                startActivity(createIntent);
            }
        });

        if(list.isEmpty()){
            cardNoData.setVisibility(View.VISIBLE);
        }
    }

    public void clearRecyclerList() {
        list.clear();
        adapter.clearList();
    }
}
