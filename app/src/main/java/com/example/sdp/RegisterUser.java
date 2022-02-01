package com.example.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner,registerUSer;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword, editTextContact1, editTextContact2;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner=(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUSer=(Button) findViewById(R.id.signUp);
        registerUSer.setOnClickListener(this);

        editTextFullName=(EditText) findViewById(R.id.fullName);
        editTextAge=(EditText) findViewById(R.id.age);
        editTextEmail=(EditText) findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);
        editTextContact1=(EditText) findViewById(R.id.eContact1);
        editTextContact2=(EditText) findViewById(R.id.eContact2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.signUp:
                registerUSer();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void registerUSer() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String fullName=editTextFullName.getText().toString().trim();
        String age=editTextAge.getText().toString().trim();
        String con1=editTextContact1.getText().toString().trim();
        String con2=editTextContact2.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Password should have more than 6 letters");
            editTextPassword.requestFocus();
            return;
        }

        if(con1.isEmpty()){
            editTextContact1.setError("Contact No is required Here");
            editTextContact1.requestFocus();
            return;
        }
        if(con1.length()<10){
            editTextContact1.setError("Enter Valid Phone number");
            editTextContact1.requestFocus();
            return;
        }

        if(con2.isEmpty()){
            editTextContact2.setError("Contact No is required Here");
            editTextContact2.requestFocus();
            return;
        }
        if(con2.length()<10){
            editTextContact2.setError("Enter Valid Phone number");
            editTextContact2.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password )
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(fullName,age,email,con1,con2);

                            FirebaseDatabase.getInstance().getReference( "Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"User has been registered succcessfully!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }else {
                                        Toast.makeText(RegisterUser.this,"Failed to register!Try again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(RegisterUser.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}