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
import com.rinaxl.Knights.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionViewActivity extends AppCompatActivity {

    private RecyclerView transactionViewRecyclerView;
    private TransactionViewAdapter transactionViewAdapter;
    private ProgressBar progressBar;
    private TextView txtTViewName;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mPatient;
    private List<Transaction> mTransactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);

        Intent intent = getIntent();
        final String patientCode = intent.getStringExtra("Patient Code");
        final String patientName = intent.getStringExtra("Patient Name");
        final String patientBranch = intent.getStringExtra("Patient Branch");


        txtTViewName = findViewById(R.id.Tview_name_txt);
        progressBar=findViewById(R.id.progress_circular);

        transactionViewRecyclerView=findViewById(R.id.transactionViewRecyclerView);
        transactionViewRecyclerView.setHasFixedSize(true);
        transactionViewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String concatPatientName = patientCode + " | " + patientName;
        txtTViewName.setText(concatPatientName);
        mTransactions=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Transactions");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTransactions.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Transaction transaction = postSnapshot.getValue(Transaction.class);
                    if(transaction.getPatientCode().equals(patientCode)){
                        mTransactions.add(transaction);
                    }
                }

                transactionViewAdapter = new TransactionViewAdapter(TransactionViewActivity.this, mTransactions);
                transactionViewRecyclerView.setAdapter(transactionViewAdapter);

               // transactionViewAdapter.setOnTransactionViewClickListener(TransactionViewActivity.this);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TransactionViewActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }
}
