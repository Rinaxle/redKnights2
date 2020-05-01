package com.rinaxl.Knights;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinaxl.Knights.Model.Transaction;

import java.util.List;

public class TransactionQueueAdapter extends RecyclerView.Adapter<TransactionQueueAdapter.TransactionQueueNameHolder> {

    private Context mContext;
    private List<Transaction> mTransactions;
    private OnTransactionQueueClickListener mListener;


    public TransactionQueueAdapter(Context mContext, List<Transaction> mTransactions) {
        this.mContext = mContext;
        this.mTransactions = mTransactions;
    }

    @NonNull
    @Override
    public TransactionQueueNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.queue_item, viewGroup,false);
        return  new TransactionQueueNameHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionQueueNameHolder transactionQueueNameHolder, int i) {
        Transaction uploadCur=mTransactions.get(i);
        String concat = uploadCur.getTransactionCode() + " | " + uploadCur.getPatientName();
        String status = uploadCur.getTransactionStart();

        transactionQueueNameHolder.txtPatientName.setText(concat);
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }


    public class TransactionQueueNameHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView txtPatientName;
        public Button StatusButton;

        public TransactionQueueNameHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName=itemView.findViewById(R.id.patientName);
            StatusButton=itemView.findViewById(R.id.StatusButton);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem editTransaction = contextMenu.add(Menu.NONE,1,1,"Edit Transaction");
            MenuItem addPayment = contextMenu.add(Menu.NONE, 1,1,"Add Payment");
            MenuItem endTransaction = contextMenu.add(Menu.NONE,3,3,"End Transaction");

            editTransaction.setOnMenuItemClickListener(this);
            endTransaction.setOnMenuItemClickListener(this);
            addPayment.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch(menuItem.getItemId()){
                        case 1:
                            mListener.onEditTransaction(position);
                            return true;
                        case 2:
                            mListener.onAddPayment(position);
                            return true;
                        case 3:
                            mListener.onEndTransaction(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }


    public interface OnTransactionQueueClickListener{
        void onItemClick(int position);
        void onEditTransaction(int position);
        void onAddPayment(int position);
        void onEndTransaction(int position);
    }

    public void setOnTransactionQueueClickListener(OnTransactionQueueClickListener listener){
        mListener = listener;
    }



}
