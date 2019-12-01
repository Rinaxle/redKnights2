package com.rinaxl.Knights;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rinaxl.Knights.Model.User;

public class LoginActivity extends AppCompatActivity {

   private FirebaseDatabase database;
   private DatabaseReference users;

    private TextInputEditText edtUserName,edtPassword;
    private MaterialButton btnLogin,btnToSignUp;
    private TextView txtSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = findViewById(R.id.username_edit_text);
        edtPassword = findViewById(R.id.password_edit_text);
        btnLogin = findViewById(R.id.login_button);
        btnToSignUp = findViewById(R.id.toSignUp_button);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtUserName.getText().toString().trim())){
                    edtUserName.setError("This field is required");
                }
                else{
                    Intent sampleIntent = new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(sampleIntent);
                    //signIn(edtUserName.getText().toString(), edtPassword.getText().toString());
                }
            }
        });

        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }

    private void signIn(final String username,final String password){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if(login.getStudentPassword().equals(password)){
                            Toast.makeText(LoginActivity.this,"Success Login",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Password Incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Username is not Registered",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
