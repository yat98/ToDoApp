package com.chandra.hidayat.todoapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class CreateActivity extends AppCompatActivity {

    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    DatabaseReference reference;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_date)
    EditText edtDate;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    private ToDo toDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        reference = FirebaseDatabase.getInstance().getReference().child("list");
        btnCreate.setOnClickListener(new View.OnClickListener() {
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
                    reference.push()
                            .setValue(toDo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CreateActivity.this, "Note Success Created", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
