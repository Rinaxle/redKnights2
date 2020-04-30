package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class NewTransactionActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference transactions;
    private DatabaseReference queue;
    private DatabaseReference patients;

    private TextView txtSetArrival,txtPatientName,txtTransactionCode;
    private TextClock trxStart;
    private MaterialButton btnAddTransaction;
    private TextInputEditText edtTransactionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        Intent intent = getIntent();
        final String patientCode = intent.getStringExtra("Patient Code");
        final String patientName = intent.getStringExtra("Patient Name");
        final String patientBranch = intent.getStringExtra("Patient Branch");

        database = FirebaseDatabase.getInstance();
        transactions = database.getReference("Transactions");
        patients = database.getReference("Patients");
        queue = database.getReference("PatientQueue/"+patientBranch);

        trxStart = findViewById(R.id.arrival);

        txtTransactionCode = findViewById(R.id.transactionCode_txtView);
        txtSetArrival = findViewById(R.id.setArrival_txtView);
        txtPatientName = findViewById(R.id.patientName_txtView);

        btnAddTransaction = findViewById(R.id.patientArrival);

        edtTransactionType = findViewById(R.id.transactionType_edit_text);

        trxStart.setFormat24Hour(null);

        txtPatientName.setText(patientName);
        setTransactionCurrentCount(patientCode);
        final String transactionKey = txtTransactionCode.getText().toString() + patientCode;

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTransaction(txtTransactionCode.getText().toString()+patientCode,patientBranch,patientName,patientCode,edtTransactionType.getText().toString(),txtTransactionCode.getText().toString(),trxStart.getText().toString());
            }
        });
    }

    private void startTransaction(final String transactionKey,final String patientBranch,final String patientName, final String patientCode,final String trxType,final String trxCode,final String trxStart) {
        if (!trxType.isEmpty()) {
            final Transaction transaction = new Transaction(transactionKey,trxStart,trxType,"nan","",trxCode,patientCode,patientName,patientBranch,"","");
            transactions.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String childname = transaction.getTransactionCode() + transaction.getPatientCode();
                    if(dataSnapshot.child(childname).exists()){
                        Toast.makeText(NewTransactionActivity.this,"Patient has an ongoing transaction!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        transactions.child(childname).setValue(transaction);
                        Toast.makeText(NewTransactionActivity.this,"Transaction Successfully Recorded",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            queue.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String childname = transaction.getTransactionCode() + transaction.getPatientCode();
                    if(dataSnapshot.child(transaction.getTransactionKey()).exists()){
                        Toast.makeText(NewTransactionActivity.this,"Patient has an ongoing transaction!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        queue.child(childname).setValue(transaction);
                        Toast.makeText(NewTransactionActivity.this,"Transaction Successfuly Recorded",Toast.LENGTH_SHORT).show();
                        Intent patientQueueIntent = new Intent(NewTransactionActivity.this,PatientQueueActivity.class);
                        startActivity(patientQueueIntent);
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
