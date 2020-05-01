package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.rinaxl.Knights.Model.Payments;

import java.util.ArrayList;
import java.util.List;

public class ViewPaymentsActivity extends AppCompatActivity {

    private RecyclerView paymentViewRecyclerView;
    private ViewPaymentsAdapter paymentViewAdapter;
    private ProgressBar progressBar;
    private TextView txtPViewName;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mPatient;
    private List<Payments> mPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);

        Intent intent = getIntent();
        final String patientCode = intent.getStringExtra("Patient Code");
        final String patientName = intent.getStringExtra("Patient Name");
        final String patientBranch = intent.getStringExtra("Patient Branch");



        txtPViewName = findViewById(R.id.Pview_name_txt);
        progressBar=findViewById(R.id.progress_circular);

        paymentViewRecyclerView=findViewById(R.id.paymentViewRecyclerView);
        paymentViewRecyclerView.setHasFixedSize(true);
        paymentViewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String concatPatientName = patientCode + " | " + patientName;
        txtPViewName.setText(concatPatientName);
        mPayments=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Payments");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPayments.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Payments payment = postSnapshot.getValue(Payments.class);
                    if(payment.getPPatientCode().equals(patientCode)){
                        mPayments.add(payment);
                    }
                }

                paymentViewAdapter = new ViewPaymentsAdapter(ViewPaymentsActivity.this, mPayments);
                paymentViewRecyclerView.setAdapter(paymentViewAdapter);

                // transactionViewAdapter.setOnTransactionViewClickListener(TransactionViewActivity.this);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewPaymentsActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });


    }
}
