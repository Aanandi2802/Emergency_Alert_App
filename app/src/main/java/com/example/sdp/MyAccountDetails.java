package com.example.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccountDetails extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    //Intent sb_login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_details);


        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();

        final TextView fullNameTextView=(TextView)findViewById(R.id.fullNameTitle);
        final TextView emailTextView=(TextView)findViewById(R.id.emailAddress);
        final TextView ageTextView=(TextView)findViewById(R.id.age1);
        final TextView con1TextView=(TextView)findViewById(R.id.contact1);
        final TextView con2TextView=(TextView)findViewById(R.id.contact2);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);

                if(userProfile!=null){
                    String fullName=userProfile.fullName;
                    String email=userProfile.email;
                    String age=userProfile.age;
                    String con1=userProfile.con1;
                    String con2=userProfile.con2;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                   con1TextView.setText(con1);
                   con2TextView.setText(con2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyAccountDetails.this,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });

    }

    /*public void update(View view){
        
        if(isNameChanged()||isPasswordChanged()){
            Toast.makeText(this,"Data has been updated",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPasswordChanged() {
    }

    private boolean isNameChanged() {

        if()

    }*/


}