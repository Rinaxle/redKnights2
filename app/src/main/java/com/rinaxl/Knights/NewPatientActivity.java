package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.Logs;
import com.rinaxl.Knights.Model.Patient;
import com.rinaxl.Knights.Model.User;

import java.util.List;

public class NewPatientActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference patients;
    private DatabaseReference logs;

    private TextView txtPatientCode;
    private AutoCompleteTextView autoTxtPatientBranch;
    private TextInputEditText edtPatientName, edtPatientAge, edtPatientBranch, edtPatientAddress;
    private MaterialButton btnAddPatient;

    private String[] branches={"Buendia ","East Avenue","Barangka","Piy Margal","Evangelista","Malate1","Malate2","Cubao"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        database = FirebaseDatabase.getInstance();
        patients = database.getReference("Patients");
        logs = database.getReference("Logs");

        btnAddPatient = findViewById(R.id.addPatient_button);
        edtPatientName = findViewById(R.id.patientName_edit_text);
        edtPatientAge = findViewById(R.id.patientAge_edit_text);
        edtPatientBranch = findViewById(R.id.patientBranch_edit_text);
        edtPatientAddress = findViewById(R.id.patientAddress_edit_text);
        txtPatientCode = findViewById(R.id.patientCode_text);
        autoTxtPatientBranch = findViewById(R.id.branch_AutoTxt);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,branches);

        autoTxtPatientBranch.setAdapter(adapter);
        autoTxtPatientBranch.setThreshold(1);

        autoTxtPatientBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoTxtPatientBranch.showDropDown();
            }
        });

        setPatientCount();

        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(txtPatientCode.getText().toString(),edtPatientName.getText().toString(),edtPatientAge.getText().toString(),edtPatientBranch.getText().toString());
            }
        });

    }

    private void setPatientCount(){
        logs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Logs patientCount = dataSnapshot.child("patient").getValue(Logs.class);
                int currentCount = Integer.parseInt(patientCount.getPatientCount());
                int secondCount = currentCount+1;
                String concatCode = String.valueOf(secondCount);
                txtPatientCode.setText(concatCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setNewPatientCount(final String patientCode){
        logs.child("patient").child("patientCount").setValue(patientCode);
    }



    private void signUp (final String patientCode,final String patientName,final String patientAge, final String patientBranch) {
        if (!patientName.isEmpty() && !patientAge.isEmpty() && !patientBranch.isEmpty()) {
            final Patient patient = new Patient(patientCode,patientName, patientAge, patientBranch,"0");

            patients.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(patient.getPatientCode()).exists()){
                        Toast.makeText(NewPatientActivity.this,"Patient Already has this Record!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        patients.child(patient.getPatientCode()).setValue(patient);
                        setNewPatientCount(patient.getPatientCode());
                        Toast.makeText(NewPatientActivity.this,"Patient Successfuly Registered",Toast.LENGTH_SHORT).show();
         //               final Logs patientCount = new Logs(patientCode);
           //             patientCount.setPatientCount(txtPatientCode.getText().toString());
                        edtPatientName.setText("");
                       edtPatientAge.setText("");
                       edtPatientAddress.setText("");
                       edtPatientBranch.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            edtPatientName.setError("This field is required!");
            edtPatientAge.setError("This field is required!");
            edtPatientBranch.setError("This field is required!");
        }
    }
}
