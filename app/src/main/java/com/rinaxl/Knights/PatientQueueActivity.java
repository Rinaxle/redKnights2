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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PatientQueueActivity extends AppCompatActivity implements PatientQueueAdapter.OnPatientQueueClickListener {

    private RecyclerView patientQueueRecyclerView;
    private PatientQueueAdapter patientQueueAdapter;
    private ProgressBar progressBar;
    private TextView txtQueueBranch;
    private TextClock trxTime;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mPatient;
    private DatabaseReference mQueue;
    private List<Transaction> mTransactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_queue);

        txtQueueBranch = findViewById(R.id.queue_branch_txt);
        trxTime = findViewById(R.id.TIME);

        patientQueueRecyclerView=findViewById(R.id.patientQueueRecyclerView);
        patientQueueRecyclerView.setHasFixedSize(true);
        patientQueueRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progress_circular);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String staffBranch = sharedPref.getString("staffBranch","");

        mPatient = FirebaseDatabase.getInstance().getReference("Transactions");
        mQueue = FirebaseDatabase.getInstance().getReference("Patients");
        txtQueueBranch.setText(staffBranch);
        trxTime.setFormat24Hour(null);

        mTransactions=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("PatientQueue/"+staffBranch);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTransactions.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Transaction patient = postSnapshot.getValue(Transaction.class);
                    mTransactions.add(patient);
                }

                patientQueueAdapter = new PatientQueueAdapter(PatientQueueActivity.this,mTransactions);
                patientQueueRecyclerView.setAdapter(patientQueueAdapter);

                patientQueueAdapter.setOnPatientQueueClickListener(PatientQueueActivity.this);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PatientQueueActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onItemClick(int position) {


    }

    @Override
    public void onSetStart(final int position) {
        final Transaction transaction = mTransactions.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(PatientQueueActivity.this);

        String message = "Time:" + trxTime.getText().toString() + "\n\n Patient:" + transaction.getPatientCode() + " | "
                + transaction.getPatientName();

        builder.setTitle("Start Treatment?")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // -insert code-
                        if(transaction.getTransactionStart().equals("nan")){
                        String date = transaction.getTransactionCode() + transaction.getPatientCode();
                        Toast.makeText(PatientQueueActivity.this,date,Toast.LENGTH_SHORT).show();
                        mDatabaseRef.child(date).child("transactionStart").setValue(trxTime.getText().toString());
                        mPatient.child(transaction.getTransactionKey()).child("transactionStart").setValue(trxTime.getText().toString());
                        }
                        else {
                            Toast.makeText(PatientQueueActivity.this, "Patient has an on-going treatment!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel",null).setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSetEnd(int position) {
        final Transaction selectedItem = mTransactions.get(position);
        final String selectedKey = selectedItem.getTransactionCode() + selectedItem.getPatientCode();
        AlertDialog.Builder builder = new AlertDialog.Builder(PatientQueueActivity.this);

        String message = "Time:" + trxTime.getText().toString() + "\n\n Patient:" + selectedItem.getPatientCode() + " | "
                + selectedItem.getPatientName() + "\n\n Start Time: " + selectedItem.getTransactionStart();

        builder.setTitle("End Treatment?")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // -insert code-
                            Toast.makeText(PatientQueueActivity.this,selectedKey,Toast.LENGTH_SHORT).show();
                            mDatabaseRef.child(selectedKey).child("transactionEnd").setValue(trxTime.getText().toString());
                            mPatient.child(selectedKey).child("transactionEnd").setValue(trxTime.getText().toString());
                            Toast.makeText(PatientQueueActivity.this, "Successful treatment!", Toast.LENGTH_SHORT).show();
                            mQueue.child(selectedItem.getPatientCode()).child("transactionCount").setValue(selectedItem.getTransactionCode());
                            mDatabaseRef.child(selectedKey).removeValue();
                    }
                })
                .setNegativeButton("Cancel",null).setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }
    }

