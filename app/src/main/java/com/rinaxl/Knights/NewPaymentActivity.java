 package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.rinaxl.Knights.Model.Payments;

public class NewPaymentActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference transactions;
    private DatabaseReference payments;
    private DatabaseReference patients;

    private TextView txtPatientName,txtPaymentCode;
    private TextClock trxDate;
    private MaterialButton btnAddPayment;
    private TextInputEditText edtNotes, edtPaymentMethod,edtPaymentAmount, edtStaff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment);


        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String staffName = sharedPref.getString("staffName","");

        Intent intent = getIntent();
        final String patientCode = intent.getStringExtra("Patient Code");
        final String patientName = intent.getStringExtra("Patient Name");
        final String patientBranch = intent.getStringExtra("Patient Branch");

        database = FirebaseDatabase.getInstance();
        transactions = database.getReference("Transactions");
        patients = database.getReference("Patients");
        payments = database.getReference("Payments");

        trxDate = findViewById(R.id.paymentDate);

        txtPaymentCode = findViewById(R.id.paymentCode);
        txtPatientName = findViewById(R.id.patientName);

        btnAddPayment = findViewById(R.id.addPayment);

        edtNotes = findViewById(R.id.paymentNote_edit_text);
        edtPaymentMethod = findViewById(R.id.paymentMethod_edit_text);
        edtPaymentAmount = findViewById(R.id.paymentAmount_edit_text);
        edtStaff = findViewById(R.id.staffNamee_edit_text);

        trxDate.setFormat24Hour(null);

        txtPatientName.setText(patientName);
        setPaymentCurrentCount(patientCode);
        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addPayment(txtPaymentCode.getText().toString(),trxDate.getText().toString(), edtNotes.getText().toString(),edtPaymentMethod.getText().toString(),edtPaymentAmount.getText().toString(),patientName,patientCode,edtStaff.getText().toString(),staffName);
            }
        });
    }

    private void addPayment(String paymentCode, String date, String paymentNotes, String paymentMethod, String paymentAmount, String patientName, String patientCode, String staffName, String accountName){
        if(!paymentNotes.isEmpty() && !paymentMethod.isEmpty() && !paymentAmount.isEmpty() && !staffName.isEmpty()){
            final Payments payment = new Payments(paymentCode,date,paymentNotes,paymentMethod,paymentAmount,patientName,patientCode,staffName,accountName);
            payments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String childKey = "P" + payment.getPaymentCode() + payment.getPPatientCode();
                    if(dataSnapshot.child(childKey).exists()){
                        Toast.makeText(NewPaymentActivity.this,"Patient already has this payment record!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        payments.child(childKey).setValue(payment);
                        Toast.makeText(NewPaymentActivity.this,"Payment Record Successfully added",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{
            edtNotes.setError("This field is required");
            edtPaymentAmount.setError("This field is required");
            edtPaymentMethod.setError("This field is required");
            edtStaff.setError("This field is required");
        }
    }



    private void setPaymentCurrentCount(final String patientCode) {
        patients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient paymentCount = dataSnapshot.child(patientCode).getValue(Patient.class);
                int currentCount = Integer.parseInt(paymentCount.getPaymentCount());
                int secondCount = currentCount+1;
                String concatCode = String.valueOf(secondCount);
                txtPaymentCode.setText(concatCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
