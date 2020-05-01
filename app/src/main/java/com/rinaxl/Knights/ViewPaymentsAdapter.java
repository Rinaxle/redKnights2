package com.rinaxl.Knights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinaxl.Knights.Model.Payments;

import java.util.List;

public class ViewPaymentsAdapter extends RecyclerView.Adapter<ViewPaymentsAdapter.ViewPaymentsViewHolder> {

    private Context mContext;
    private List<Payments> mPayments;

    public ViewPaymentsAdapter(Context mContext, List<Payments> mPayments) {
        this.mContext = mContext;
        this.mPayments = mPayments;
    }

    @NonNull
    @Override
    public ViewPaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.patient_item, viewGroup, false);
        return new ViewPaymentsViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewPaymentsViewHolder viewPaymentsViewHolder, int i) {
        Payments uploadCur = mPayments.get(i);
        String concat = uploadCur.getPaymentDate() + "     " + uploadCur.getPaymentNotes();
        viewPaymentsViewHolder.txtPatientName.setText(concat);
    }

    @Override
    public int getItemCount() {
        return mPayments.size();
    }

    public class ViewPaymentsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPatientName;

        public ViewPaymentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName = itemView.findViewById(R.id.patientName);

        }
    }
}
