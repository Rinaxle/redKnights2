package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.Inventory;
import com.rinaxl.Knights.Model.Logs;

public class NewInventoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference inventory;
    private DatabaseReference logs;

    private TextView txtInventoryCode, txtBranch;
    private TextClock trxDate;
    private MaterialButton btnAddInventory;
    private TextInputEditText edtItem, edtQuantity,edtItemAmount, edtStaff;
    private Spinner sprItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inventory);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String staffName = sharedPref.getString("staffName","");
        final String staffBranch = sharedPref.getString("staffBranch","");

        database = FirebaseDatabase.getInstance();
        inventory = database.getReference("Inventory");
        logs = database.getReference("Logs");

        trxDate = findViewById(R.id.paymentDate);

        txtInventoryCode = findViewById(R.id.inventoryCode);
        txtBranch = findViewById(R.id.inventoryBranch);

        btnAddInventory = findViewById(R.id.addInventory);

        edtItem = findViewById(R.id.item_edit_text);
        edtQuantity = findViewById(R.id.quantity_edit_text);
        edtItemAmount = findViewById(R.id.itemAmount_edit_text);
        edtStaff = findViewById(R.id.staff_edit_text);

        sprItemType = findViewById(R.id.itemType_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemType_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprItemType.setAdapter(adapter);
        sprItemType.setOnItemSelectedListener(this);

        trxDate.setFormat24Hour(null);

        setInventoryCount();


        btnAddInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInventory(txtInventoryCode.getText().toString(),trxDate.getText().toString(),edtItem.getText().toString(),edtQuantity.getText().toString(),sprItemType.getSelectedItem().toString(),edtItemAmount.getText().toString(),staffBranch,edtStaff.getText().toString(),staffName);
            }
        });
    }

    private void setInventoryCount(){
        logs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Logs inventoryCount = dataSnapshot.child("inventory").getValue(Logs.class);
                int currentCount = Integer.parseInt(inventoryCount.getInventoryCount());
                int secondCount = currentCount+1;
                String concatCode = String.valueOf(secondCount);
                txtInventoryCode.setText(concatCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void addInventory(final String inventoryCode, final String date, final String item, final String itemType, final String quantity, final String amount, final String branch, final String staffName, final String accountName){
        if(!item.isEmpty() && !quantity.isEmpty() && !amount.isEmpty() && !staffName.isEmpty()){
            final Inventory inventory1 = new Inventory(inventoryCode, date,item,itemType,quantity,amount,branch,staffName,accountName);

            inventory.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(inventory1.getInventoryCode()).exists()){
                        Toast.makeText(NewInventoryActivity.this,"Cannot make duplicate inventory!!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        inventory.child(inventory1.getInventoryCode()).setValue(inventory1);
                        setNewInventoryCount(inventory1.getInventoryCode());
                        Toast.makeText(NewInventoryActivity.this,"Inventory added",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setNewInventoryCount(final String patientCode){
        logs.child("inventory").child("inventoryCount").setValue(patientCode);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
