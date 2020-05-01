package com.rinaxl.Knights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinaxl.Knights.Model.Transaction;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TransactionViewAdapter extends RecyclerView.Adapter<TransactionViewAdapter.TransactionVViewHolder> {

    private Context mContext;
    private List<Transaction> mTransactions;

    public TransactionViewAdapter(Context mContext, List<Transaction> mTransactions) {
        this.mContext = mContext;
        this.mTransactions = mTransactions;
    }

    @NonNull
    @Override
    public TransactionVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View v= LayoutInflater.from(mContext).inflate(R.layout.transaction_item, viewGroup,false);
        return  new TransactionVViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull TransactionVViewHolder transactionVViewHolder, int i){
        Transaction trxCurrent = mTransactions.get(i);
        String concatTitle = trxCurrent.getTransactionKey() + " - " + trxCurrent.getPatientBranch();
        String concatName = trxCurrent.getPatientName();
        String concatArrival = "Arrival at:     " + trxCurrent.getTransactionDate();
        String concatStart = "Treatment at:     " + trxCurrent.getTransactionStart();
        String concatEnd = "Treatment End at:     " + trxCurrent.getTransactionEnd();
        String concatComment = trxCurrent.getImgName();

        transactionVViewHolder.transactionTitle.setText(concatTitle);
        transactionVViewHolder.patientName.setText(concatName);
        transactionVViewHolder.patientArrival.setText(concatArrival);
        transactionVViewHolder.patientStart.setText(concatStart);
        transactionVViewHolder.patientEnd.setText(concatEnd);
        transactionVViewHolder.patientComment.setText(concatComment);

        Picasso.get()
                .load(trxCurrent.getImgUrl())
                // .placeholder(R.drawable.imagepreview)
                .fit()
                .centerCrop()
                .into(transactionVViewHolder.transactionView);

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public class TransactionVViewHolder extends RecyclerView.ViewHolder {
        public TextView transactionTitle,patientName,patientArrival,patientStart,patientEnd,patientComment ;
        public ImageView transactionView;

        public TransactionVViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionTitle=itemView.findViewById(R.id.transactionTitle);
            patientName=itemView.findViewById(R.id.patientName);
            patientArrival=itemView.findViewById(R.id.patientArrival);
            patientStart=itemView.findViewById(R.id.patientStart);
            patientEnd=itemView.findViewById(R.id.patientEnd);
            patientComment=itemView.findViewById(R.id.patientComment);
            transactionView=itemView.findViewById(R.id.transactionView);
        }
    }

}
