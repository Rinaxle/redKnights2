package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.Patient;

import java.util.ArrayList;
import java.util.List;

public class ViewPatientActivity extends AppCompatActivity implements PatientAdapter.OnPatientClickListener {

    private RecyclerView patientRecyclerView;
    private PatientAdapter patientAdapter;
    private ProgressBar progressBar;

    private DatabaseReference mDatabaseRef;
    private List<Patient> mPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        patientRecyclerView=findViewById(R.id.patientRecyclerView);
        patientRecyclerView.setHasFixedSize(true);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progress_circular);

        mPatients=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Patients");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPatients.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Patient patient = postSnapshot.getValue(Patient.class);
                    mPatients.add(patient);
                }

                patientAdapter=new PatientAdapter(ViewPatientActivity.this, mPatients);
                patientRecyclerView.setAdapter(patientAdapter);

                patientAdapter.setOnPatientClickListener(ViewPatientActivity.this);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewPatientActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPatientActivity.this);

        String message = "\n\n Patient Number: "+ patient.getPatientCode() + "\n\n Patient Name: "+ patient.getPatientName() + "\n\n Patient Age: "+ patient.getPatientAge()
                + "\n\n Branch: "+ patient.getPatientBranch();

        builder.setTitle("Patient Information")
                .setMessage(message)
                .setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onWhateverClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPatientActivity.this);
        Patient patient = mPatients.get(position);
        final String patientCode = patient.getPatientCode();
        final String patientName = patient.getPatientName();
        String Title = "Patient: " + patientCode;
        builder.setTitle(Title)
                .setMessage("Are you sure you want to add transaction to this patient?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // -insert code-
                        Intent intent = new Intent(ViewPatientActivity.this, TransactionActivity.class);
                        intent.putExtra("Patient Code", patientCode);
                        intent.putExtra("Patient Name", patientName);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null).setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDeleteClick(int position) {

    }
}
