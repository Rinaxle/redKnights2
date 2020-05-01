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

public class TransactionQueueActivity extends AppCompatActivity implements TransactionQueueAdapter.OnTransactionQueueClickListener{

    private RecyclerView transactionQueueRecyclerView;
    private TransactionQueueAdapter transactionQueueAdapter;
    private ProgressBar progressBar;
    private TextView txtTQueueBranch;
    private TextClock trxTime;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mPatient;
    private List<Transaction> mTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_queue);

        txtTQueueBranch = findViewById(R.id.Tqueue_branch_txt);
        trxTime = findViewById(R.id.TIME);

        transactionQueueRecyclerView=findViewById(R.id.transactionQueueRecyclerView);
        transactionQueueRecyclerView.setHasFixedSize(true);
        transactionQueueRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progress_circular);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String staffBranch = sharedPref.getString("staffBranch","");

        mPatient = FirebaseDatabase.getInstance().getReference("Patients");

        txtTQueueBranch.setText(staffBranch);
        trxTime.setFormat24Hour(null);

        mTransactions=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Transactions");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTransactions.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Transaction patient = postSnapshot.getValue(Transaction.class);
                    String transactionEnd = patient.getTransactionEnd();
                    String image = patient.getImgName();

                    if(image.equals("")) {
                        mTransactions.add(patient);
                    }
                }

                transactionQueueAdapter = new TransactionQueueAdapter(TransactionQueueActivity.this,mTransactions);
                transactionQueueRecyclerView.setAdapter(transactionQueueAdapter);

                transactionQueueAdapter.setOnTransactionQueueClickListener(TransactionQueueActivity.this);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TransactionQueueActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onItemClick(int position) {


    }

    @Override
    public void onEditTransaction(final int position) {
        final Transaction transaction = mTransactions.get(position);
        Intent intent = new Intent(TransactionQueueActivity.this, Home.class);
        intent.putExtra("Transaction Key", transaction.getTransactionKey());
        startActivity(intent);
    }

    @Override
    public void onAddPayment(int position) {
        final Transaction selectedItem = mTransactions.get(position);
        final String selectedKey = selectedItem.getTransactionCode() + selectedItem.getPatientCode();

    }

    @Override
    public void onEndTransaction(int position) {
        final Transaction selectedItem = mTransactions.get(position);
        final String selectedKey = selectedItem.getTransactionCode() + selectedItem.getPatientCode();

    }

}
