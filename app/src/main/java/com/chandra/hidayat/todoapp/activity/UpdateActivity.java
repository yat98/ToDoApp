package com.chandra.hidayat.todoapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chandra.hidayat.todoapp.R;
import com.chandra.hidayat.todoapp.model.ToDo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateActivity extends AppCompatActivity {
    public static final String EXTRA_TODO = "extra_todo";
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_date)
    EditText edtDate;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    private DatabaseReference reference;
    private ToDo toDo;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        ToDo toDoUpdate = getIntent().getParcelableExtra(EXTRA_TODO);
        key = toDoUpdate.getKey();
        edtTitle.setText(toDoUpdate.getTitle());
        edtDate.setText(toDoUpdate.getDate());
        edtDescription.setText(toDoUpdate.getDescription());
        reference = FirebaseDatabase.getInstance().getReference().child("list");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle = edtTitle.getText().toString();
                String inputDate = edtDate.getText().toString();
                String inputDescription = edtDescription.getText().toString();

                boolean isEmpty = false;

                if (TextUtils.isEmpty(inputTitle)) {
                    isEmpty = true;
                    edtTitle.setError("Title cannot be null");
                    edtTitle.requestFocus();
                } else if (TextUtils.isEmpty(inputDate)) {
                    isEmpty = true;
                    edtDate.setError("Date cannot be null");
                    edtDate.requestFocus();
                } else if (TextUtils.isEmpty(inputDescription)) {
                    isEmpty = true;
                    edtDescription.setError("Description cannot be null");
                    edtDescription.requestFocus();
                }

                if (!isEmpty) {
                    toDo = new ToDo();
                    toDo.setTitle(inputTitle);
                    toDo.setDate(inputDate);
                    toDo.setDescription(inputDescription);
                    reference.child(key)
                            .setValue(toDo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UpdateActivity.this, "Note Update Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(key)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateActivity.this, "Note Delete Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }
}
