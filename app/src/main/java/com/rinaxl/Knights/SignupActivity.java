package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.User;

public class SignupActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference users;

    private TextInputEditText edtName, edtUserName,edtPassword;
    private AutoCompleteTextView txtAutoBranch, txtAutoPrivilege;
    private MaterialButton btnSignUp,btnHint;

    private String[] branches={"Buendia","East Avenue","Barangka","Piy Margal","Evangelista","Malate1","Malate2","Cubao"};
    private String[] privilege={"0","1","2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        btnSignUp = findViewById(R.id.signup_button);
        btnHint = findViewById(R.id.hint_button);

        edtName = findViewById(R.id.name_edit_text);
        edtUserName = findViewById(R.id.userName_edit_text);
        edtPassword = findViewById(R.id.password_edit_text);

        txtAutoBranch = findViewById(R.id.userBranch_AutoTxt);
        txtAutoPrivilege = findViewById(R.id.userPrivilege_AutoTxt);


        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,branches);

        txtAutoBranch.setAdapter(adapter);
        txtAutoBranch.setThreshold(1);

        txtAutoBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtAutoBranch.showDropDown();
            }
        });


        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,privilege);

        txtAutoPrivilege.setAdapter(adapter1);
        txtAutoPrivilege.setThreshold(1);

        txtAutoPrivilege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtAutoPrivilege.showDropDown();
            }
        });


        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

                String message =
                        " 0 - HyperAdmin\n\n" +
                                "   " + "Can do Anything (Create User, Delete Patient, Add Patient) \n\n" +
                        " 1 - StaffAdmin\n\n" +
                                "   " + "Limited (Add Patient, Delete Patient) \n\n" +
                        " 2 - Admin\n\n" +
                                "   " + "Restricted (Add Patient)";

                builder.setTitle("Privilege Legend")
                        .setMessage(message)
                        .setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(edtName.getText().toString(),edtUserName.getText().toString(),edtPassword.getText().toString(),txtAutoBranch.getText().toString(),txtAutoPrivilege.getText().toString());
            }
        });
    }

    private void signUp(final String Name, final String Username,final String Password,final String UserBranch,final String UserPrivilege){
        if(!Username.isEmpty() && !Password.isEmpty()){
            final User user = new User(Username,Name,Password,UserPrivilege,UserBranch);

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user.getStaffUsername()).exists()){
                        Toast.makeText(SignupActivity.this,"Username Already Exists",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        users.child(user.getStaffUsername()).setValue(user);
                        Toast.makeText(SignupActivity.this,"User Successfuly Registered",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            edtUserName.setError("This field is required!");
            edtPassword.setError("This field is required!");
            edtName.setError("This field is required");
        }
    }
}
