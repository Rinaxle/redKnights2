package com.rinaxl.Knights;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinaxl.Knights.Model.Patient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientNameHolder> {
    private Context mContext;
    private List<Patient> mPatients;
    private OnPatientClickListener mListener;

    public PatientAdapter(Context context, List<Patient> patients)
    {
        mContext=context;
        mPatients=patients;
    }

    @NonNull
    @Override
    public PatientNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.patient_item, viewGroup,false);
        return  new PatientNameHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientNameHolder patientNameHolder, int i) {
        Patient uploadCur=mPatients.get(i);
        String concat = uploadCur.getPatientCode() + " | " + uploadCur.getPatientName();
        patientNameHolder.txtPatientName.setText(concat);
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public class PatientNameHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView txtPatientName;

        public PatientNameHolder(@NonNull View itemView) {
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
            MenuItem getPatientCode = contextMenu.add(Menu.NONE,1,1,"Select Patient");
            MenuItem delete = contextMenu.add(Menu.NONE,2,2,"Delete Patient");

            getPatientCode.setOnMenuItemClickListener(this);
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
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnPatientClickListener{
        void onItemClick(int position);
        void onWhateverClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnPatientClickListener(OnPatientClickListener listener){
        mListener = listener;
    }

}