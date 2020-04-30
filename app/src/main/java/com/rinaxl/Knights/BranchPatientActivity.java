package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.Patient;

import java.util.ArrayList;
import java.util.List;

public class BranchPatientActivity extends AppCompatActivity implements BranchPatientAdapter.OnBranchPatientClickListener{

    private RecyclerView branchPatientRecyclerView;
    private BranchPatientAdapter branchPatientAdapter;
    private ProgressBar progressBar;
    private TextView txtUserBranch;

    private DatabaseReference mDatabaseRef;
    private List<Patient> mPatients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_patient);

        txtUserBranch = findViewById(R.id.user_branch_txt);

        branchPatientRecyclerView=findViewById(R.id.branchPatientRecyclerView);
        branchPatientRecyclerView.setHasFixedSize(true);
        branchPatientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progress_circular);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String staffBranch = sharedPref.getString("staffBranch","");

        txtUserBranch.setText(staffBranch);

        mPatients=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Patients");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPatients.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Patient patient = postSnapshot.getValue(Patient.class);
                    String patientBranch = patient.getPatientBranch();
                    if (patientBranch.equals(staffBranch)) {
                        mPatients.add(patient);
                    }}

                branchPatientAdapter = new BranchPatientAdapter(BranchPatientActivity.this,mPatients);
                branchPatientRecyclerView.setAdapter(branchPatientAdapter);

                branchPatientAdapter.setOnBranchPatientClickListener(BranchPatientActivity.this);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BranchPatientActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        AlertDialog.Builder builder = new AlertDialog.Builder(BranchPatientActivity.this);

        String message = "Patient Number: "+ patient.getPatientCode() + "\n\n Patient Name: "+ patient.getPatientName() + "\n\n Patient Age: "+ patient.getPatientAge()
                + "\n\n Branch: "+ patient.getPatientBranch() +"\n\n Number of Transactions: "+ patient.getTransactionCount();

        builder.setTitle("Patient Information")
                .setMessage(message)
                .setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onWhateverClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BranchPatientActivity.this);
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        final String patientName = patient.getPatientName();
        final String patientBranch = patient.getPatientBranch();
        String Title = "Patient: " + patientCode;
        builder.setTitle(Title)
                .setMessage("Are you sure you want to add transaction to this patient?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // -insert code-
                        Intent intent = new Intent(BranchPatientActivity.this, NewTransactionActivity.class);
                        intent.putExtra("Patient Code", patientCode);
                        intent.putExtra("Patient Name", patientName);
                        intent.putExtra("Patient Branch", patientBranch);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null).setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onViewTransaction(int position){
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        final String patientName = patient.getPatientName();
        final String patientBranch = patient.getPatientBranch();
        String Title = "Patient: " + patientCode;
        Intent intent = new Intent(BranchPatientActivity.this, TransactionViewActivity.class);
        intent.putExtra("Patient Code", patientCode);
        intent.putExtra("Patient Name", patientName);
        intent.putExtra("Patient Branch", patientBranch);
        startActivity(intent);
    }

    @Override
    public void onAddPayments(int position){
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        final String patientName = patient.getPatientName();
        final String patientBranch = patient.getPatientBranch();
        String Title = "Patient: " + patientCode;
        Intent intent = new Intent(BranchPatientActivity.this, NewPaymentActivity.class);
        intent.putExtra("Patient Code", patientCode);
        intent.putExtra("Patient Name", patientName);
        intent.putExtra("Patient Branch", patientBranch);
        startActivity(intent);
    }

    @Override
    public void onViewPayments(int position){
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        final String patientName = patient.getPatientName();
        final String patientBranch = patient.getPatientBranch();
        Intent intent = new Intent(BranchPatientActivity.this, ViewPaymentsActivity.class);
        intent.putExtra("Patient Code", patientCode);
        intent.putExtra("Patient Name", patientName);
        intent.putExtra("Patient Branch", patientBranch);
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {

    }
}
