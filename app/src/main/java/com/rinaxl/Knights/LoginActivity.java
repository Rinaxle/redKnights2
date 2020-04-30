package com.rinaxl.Knights;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
                    signIn(edtUserName.getText().toString(), edtPassword.getText().toString());
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

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                        if(login.getStaffPassword().equals(password)){
                            Toast.makeText(LoginActivity.this,"Success Login",Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor p = sharedPref.edit();
                            p.putString("staffUsername",login.getStaffUsername());
                            p.putString("staffName",login.getStaffName());
                            p.putString("staffBranch",login.getStaffBranch());
                            p.putString("staffPrivilege",login.getStaffPrivilege());
                            p.apply();
                            LoginLog();
                            Intent loginIntent = new Intent(LoginActivity.this,MenuActivity.class);
                            startActivity(loginIntent);
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Password Incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"Username is not Registered",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void LoginLog() {
        String ipAddress = getLocalIpAddress();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
