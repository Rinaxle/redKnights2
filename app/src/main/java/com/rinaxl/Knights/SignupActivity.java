package com.rinaxl.Knights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    private TextInputEditText edtName, edtLastName, edtUserName, edtPassword;
    private MaterialButton btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        btnSignUp = findViewById(R.id.signup_button);
        edtName = findViewById(R.id.name_edit_text);
        edtLastName = findViewById(R.id.lastName_edit_text);
        edtUserName = findViewById(R.id.username_edit_text);
        edtPassword = findViewById(R.id.password_edit_text);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(edtName.getText().toString(),edtLastName.getText().toString(),edtUserName.getText().toString(),edtPassword.getText().toString());
            }
        });
    }

    private void signUp(final String Name,final String LastName, final String Username,final String Password){
        if(!Username.isEmpty() && !Password.isEmpty()){
            final User user = new User(Username,Password);

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user.getStudentUsername()).exists()){
                        Toast.makeText(SignupActivity.this,"Username Already Exists",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        users.child(user.getStudentUsername()).setValue(user);
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
            edtLastName.setError("This field is required");
        }
    }
}
