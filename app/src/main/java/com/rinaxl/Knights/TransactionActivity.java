package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.Patient;
import com.rinaxl.Knights.Model.Transaction;

public class TransactionActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference transactions;
    private DatabaseReference patients;

    private TextView txtSetArrival,txtPatientName,txtSetStart,txtSetEnd,txtTransactionCode;
    private TextClock trxStart,trxEnd,pntArrival;
    private MaterialButton btnAddTransaction,btnPatientArrival,btnStartTime,btnEndTime;
    private TextInputEditText edtTransactionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();
        final String patientCode = intent.getStringExtra("Patient Code");
        final String patientName = intent.getStringExtra("Patient Name");

        database = FirebaseDatabase.getInstance();
        transactions = database.getReference("Patients/"+patientCode+"/transactions");
        patients = database.getReference("Patients");

        pntArrival = findViewById(R.id.arrival);
        trxStart = findViewById(R.id.currentTime);
        trxEnd = findViewById(R.id.currentEndTime);
        txtTransactionCode = findViewById(R.id.transactionCode_txtView);

        txtSetArrival = findViewById(R.id.setArrival_txtView);
        txtPatientName = findViewById(R.id.patientName_txtView);
        txtSetStart = findViewById(R.id.setStart_txtView);
        txtSetEnd = findViewById(R.id.setEnd_txtView);

        btnPatientArrival = findViewById(R.id.patientArrival);
        btnStartTime = findViewById(R.id.transactionStart);
        btnEndTime = findViewById(R.id.transactionEnd);
        btnAddTransaction = findViewById(R.id.addTransaction_button);

        edtTransactionType = findViewById(R.id.transactionType_edit_text);

        pntArrival.setFormat24Hour(null);
        trxStart.setFormat24Hour(null);
        trxEnd.setFormat24Hour(null);

        txtPatientName.setText(patientName);
        setTransactionCurrentCount(patientCode);
        final String TransactionCode = txtTransactionCode.getText().toString();

        btnPatientArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             txtSetArrival.setText(pntArrival.getText());
             transactions.child(txtTransactionCode.getText().toString()).child("transactionDate").setValue(pntArrival.getText());
            }
        });

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trxStart.setText(trxStart.getText());
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trxEnd.setText(trxEnd.getText());
            }
        });

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTransaction(edtTransactionType.getText().toString(),txtTransactionCode.getText().toString());
            }

            private void startTransaction(final String trxType,final String trxCode) {
                if (!trxType.isEmpty()) {
                    final Transaction transaction = new Transaction("",trxType,"","",trxCode);
                    transactions.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(transaction.getTransactionType()).exists()){
                                Toast.makeText(TransactionActivity.this,"Patient Already has a Record!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                btnPatientArrival.setEnabled(true);
                                btnStartTime.setEnabled(true);
                                btnEndTime.setEnabled(true);
                                btnAddTransaction.setText("Add Transaction");

                                transactions.child(transaction.getTransactionCode()).setValue(transaction);
                                Toast.makeText(TransactionActivity.this,"Transaction Successfuly Registered",Toast.LENGTH_SHORT).show();
                                ContentChecker(trxCode);
                                edtTransactionType.setText(transaction.getTransactionType());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    edtTransactionType.setError("This field is required!");
                }
            }
        });



    }

    private void ContentChecker(final String trxCode) {
        transactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Transaction patientTime = dataSnapshot.child(trxCode).getValue(Transaction.class);
                String patientArrival = patientTime.getTransactionDate();
                String patientStart = patientTime.getTransactionStart();
                String patientEnd = patientTime.getTransactionEnd();

                if (patientArrival != ""){
                    btnPatientArrival.setEnabled(false);
                    txtSetArrival.setText(patientArrival);
                }
                if (patientStart != ""){
                    btnStartTime.setEnabled(false);
                    txtSetStart.setText(patientArrival);
                }
                if (patientEnd != ""){
                    btnEndTime.setEnabled(false);
                    txtSetEnd.setText(patientArrival);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setTransactionCurrentCount(final String patientCode) {
        patients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient transactionCount = dataSnapshot.child(patientCode).getValue(Patient.class);
                int currentCount = Integer.parseInt(transactionCount.getTransactionCount());
                int secondCount = currentCount+1;
                String concatCode = String.valueOf(secondCount);
                txtTransactionCode.setText(concatCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
    });
    }

}
