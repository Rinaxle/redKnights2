package com.rinaxl.Knights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.rinaxl.Knights.Model.PatientQueue;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton btnAddPatient,btnAllPatient,btnBranchPatient,btnSample,btnTransaction, btnInventory;
    private AppCompatTextView txtName,txtBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAddPatient = findViewById(R.id.addPatient_button);
        btnAllPatient = findViewById(R.id.allPatient_button);
        btnBranchPatient = findViewById(R.id.branchPatient_button);
        btnSample = findViewById(R.id.sample_button);
        btnTransaction = findViewById(R.id.transaction_button);
        btnInventory = findViewById(R.id.inventory_button);

        txtBranch = findViewById(R.id.userBranch_txt);
        txtName = findViewById(R.id.name_txt);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String staffName = sharedPref.getString("staffName","");
        String staffBranch = sharedPref.getString("staffBranch","");


        String name = "Hello " + staffName + ", you are in";
        String branch = staffBranch + " Branch";

        txtBranch.setText(branch);
        txtName.setText(name);

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
                Intent branchPatientIntent = new Intent(MenuActivity.this, BranchPatientActivity.class);
                startActivity(branchPatientIntent);
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
                Intent sampleIntent = new Intent(MenuActivity.this, PatientQueueActivity.class);
                startActivity(sampleIntent);
            }
        });

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transactionIntent = new Intent(MenuActivity.this,TransactionQueueActivity.class);
                startActivity(transactionIntent);
            }
        });

        btnInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inventoryIntent = new Intent(MenuActivity.this,NewInventoryActivity.class);
                startActivity(inventoryIntent);
            }
        });
    }
}
