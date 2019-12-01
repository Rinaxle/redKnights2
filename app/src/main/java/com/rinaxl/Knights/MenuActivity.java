package com.rinaxl.Knights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton btnAddPatient,btnAllPatient,btnBranchPatient,btnSample,btnTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAddPatient = findViewById(R.id.addPatient_button);
        btnAllPatient = findViewById(R.id.allPatient_button);
        btnBranchPatient = findViewById(R.id.branchPatient_button);
        btnSample = findViewById(R.id.sample_button);
        btnTransaction = findViewById(R.id.transaction_button);


        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPatientIntent = new Intent(MenuActivity.this,NewPatientActivity.class);
                startActivity(addPatientIntent);
            }
        });

        btnBranchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   -insert intent-
            }
        });

        btnAllPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent patientIntent = new Intent(MenuActivity.this, ViewPatientActivity.class);
                startActivity(patientIntent);
            }
        });

        btnSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(MenuActivity.this,Home.class);
                startActivity(sampleIntent);
            }
        });

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transactionIntent = new Intent(MenuActivity.this,TransactionActivity.class);
                startActivity(transactionIntent);
            }
        });
    }
}
