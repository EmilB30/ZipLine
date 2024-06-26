package com.eapps.zipline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://zipline-eapps-default-rtdb.europe-west1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final EditText username = findViewById(R.id.r_username);
        final EditText email = findViewById(R.id.r_email);
        final EditText password = findViewById(R.id.r_password);
        final AppCompatButton signupButton = findViewById(R.id.r_signButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user=username.getText().toString();
                final String emailAddress=email.getText().toString();
                final String pass=password.getText().toString();

                if(user.isEmpty() || emailAddress.isEmpty() || pass.isEmpty()){
                    if(user.isEmpty()){
                        Toast.makeText(Register.this, "Username is empty!" , Toast.LENGTH_SHORT).show();
                    }
                    if(emailAddress.isEmpty()){
                        Toast.makeText(Register.this, "Email is empty!" , Toast.LENGTH_SHORT).show();
                    }
                    if(pass.isEmpty()){
                        Toast.makeText(Register.this, "Password is empty!" , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("users").hasChild(user)){
                                Toast.makeText(Register.this, "Username taken!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(user).child("email").setValue(emailAddress);
                                databaseReference.child("users").child(user).child("password").setValue(pass);
                                Toast.makeText(Register.this, "Registered!",Toast.LENGTH_SHORT).show();

                                Intent intent= new Intent(Register.this,MainActivity.class);
                                intent.putExtra("username",user);
                                intent.putExtra("email",emailAddress);
                                intent.putExtra("password",pass);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
    }
}