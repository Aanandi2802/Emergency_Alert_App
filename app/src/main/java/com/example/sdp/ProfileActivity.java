package com.example.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private Button logout;
    private Button MyAccount;

    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_MESSAGE = 1;
    private TextView callText,callText1,callText2;
    private Button callBtn,callBtn1,callBtn2;
    private Button sensMsg;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    /*private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;*/

    EditText mobileno,message;
    Button sendsms;

    //private ArrayList<String> number = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout=(Button)findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });


        MyAccount=(Button)findViewById(R.id.myAccount);
        MyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,MyAccountDetails.class));
            }
        });

        callText = findViewById(R.id.callTxt);
        callText1 = findViewById(R.id.callTxt1);
        callText2 = findViewById(R.id.callTxt2);


        callBtn = findViewById(R.id.callButton);
        callBtn1 = findViewById(R.id.callButton1);
        callBtn2 = findViewById(R.id.callButton2);


        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton();
            }
        });

        callBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton1();
            }
        });
        callBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton2();
            }
        });
        //------------------------------------------------------------------------
        sensMsg=findViewById(R.id.button1);
        sensMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }

    private void sendMessage() {
        String no="6353276703";
        String no1="7043641688";
        String msg="HELP ME!!! I'M IN DANGER";


        if (no.trim().length() > 0 && no1.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_MESSAGE);
            } else {

                //Getting intent and PendingIntent instance
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi,null);

                SmsManager sms1=SmsManager.getDefault();
                sms.sendTextMessage(no1, null, msg, pi,null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!",Toast.LENGTH_LONG).show();
            }
        }

    }

    private void CallButton() {
        String number = "6353276703";
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void CallButton1() {
        String number = "9904945151";
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void CallButton2() {
        String number = "7043641688";
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CallButton();
                CallButton1();
                CallButton2();

            } else {
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_MESSAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMessage();

            } else {
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }
}