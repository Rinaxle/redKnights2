package com.rinaxl.Knights;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinaxl.Knights.Model.Patient;

import java.util.List;

public class BranchPatientAdapter extends RecyclerView.Adapter<BranchPatientAdapter.BranchPatientNameHolder>{
    private Context mContext;
    private List<Patient> mPatients;
    private OnBranchPatientClickListener mListener;


    public BranchPatientAdapter(Context context, List<Patient> patients)
    {
        mContext=context;
        mPatients=patients;
    }

    @NonNull
    @Override
    public BranchPatientNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.patient_item, viewGroup,false);
        return  new BranchPatientNameHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchPatientNameHolder branchPatientNameHolder, int i) {
        Patient uploadCur=mPatients.get(i);
        String concat = uploadCur.getPatientCode() + " | " + uploadCur.getPatientName();
        branchPatientNameHolder.txtPatientName.setText(concat);
    }


    @Override
    public int getItemCount() {
        return mPatients.size();
    }


    public class BranchPatientNameHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView txtPatientName;

        public BranchPatientNameHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName=itemView.findViewById(R.id.patientName);

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
            MenuItem getPatientCode = contextMenu.add(Menu.NONE,1,1,"Add Transaction to Patient");
            MenuItem viewTransaction = contextMenu.add(Menu.NONE,2,2,"View Transactions");
            MenuItem addPayment = contextMenu.add(Menu.NONE,3,3,"Add Payment to Patient");
            MenuItem viewPayments = contextMenu.add(Menu.NONE,4,4,"View Payments");
            MenuItem delete = contextMenu.add(Menu.NONE,5,5,"Delete Patient ");


            getPatientCode.setOnMenuItemClickListener(this);
            viewTransaction.setOnMenuItemClickListener(this);
            addPayment.setOnMenuItemClickListener(this);
            viewPayments.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch(menuItem.getItemId()){
                        case 1:
                            mListener.onWhateverClick(position);
                            return true;
                        case 2:
                            mListener.onViewTransaction(position);
                            return true;
                        case 3:
                            mListener.onAddPayments(position);
                            return true;
                        case 4:
                            mListener.onViewPayments(position);
                            return true;
                        case 5:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnBranchPatientClickListener{
        void onItemClick(int position);
        void onWhateverClick(int position);
        void onViewTransaction(int position);
        void onAddPayments(int position);
        void onViewPayments(int position);
        void onDeleteClick(int position);
    }

    public void setOnBranchPatientClickListener(OnBranchPatientClickListener listener){
        mListener = listener;
    }

}
