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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rinaxl.Knights.Model.Transaction;

import java.util.List;

import static android.graphics.Color.parseColor;

public class PatientQueueAdapter extends RecyclerView.Adapter<PatientQueueAdapter.PatientQueueNameHolder> {

    private Context mContext;
    private List<Transaction> mTransactions;
    private OnPatientQueueClickListener mListener;


    public PatientQueueAdapter(Context context, List<Transaction> transactions)
    {
        mContext=context;
        mTransactions=transactions;
    }

    @NonNull
    @Override
    public PatientQueueNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.queue_item, viewGroup,false);
        return  new PatientQueueNameHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientQueueNameHolder patientQueueNameHolder, int i) {
        Transaction uploadCur=mTransactions.get(i);
        String concat = uploadCur.getTransactionCode() + " | " + uploadCur.getPatientName();
        String status = uploadCur.getTransactionStart();

        patientQueueNameHolder.txtPatientName.setText(concat);
    }



    @Override
    public int getItemCount() {
        return mTransactions.size();
    }


    public class PatientQueueNameHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView txtPatientName;
        public Button StatusButton;

        public PatientQueueNameHolder(@NonNull View itemView) {
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
            MenuItem setStart = contextMenu.add(Menu.NONE,1,1,"Start Treatment");
            MenuItem setEnd = contextMenu.add(Menu.NONE,2,2,"End Treatment");

            setStart.setOnMenuItemClickListener(this);
            setEnd.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch(menuItem.getItemId()){
                        case 1:
                            mListener.onSetStart(position);
                            return true;
                        case 2:
                            mListener.onSetEnd(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnPatientQueueClickListener{
        void onItemClick(int position);
        void onSetStart(int position);
        void onSetEnd(int position);
    }

    public void setOnPatientQueueClickListener(OnPatientQueueClickListener listener){
        mListener = listener;
    }
}
