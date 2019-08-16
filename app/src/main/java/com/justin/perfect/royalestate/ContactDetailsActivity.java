package com.justin.perfect.royalestate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.justin.perfect.royalestate.util.Buyer;

import java.text.SimpleDateFormat;
import java.util.Date;


import dmax.dialog.SpotsDialog;

public class ContactDetailsActivity extends AppCompatActivity {

    private static final String TAG = "idontkniwwhatowritehere";
    String area,city,position,bedrooms,acres,sqft,min,max,uniqid,photo,phone;
    FirebaseUser user;
    EditText userName_et,userEmail_et;
    Button makeRequest_btn;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        area = getIntent().getStringExtra("area");
        city = getIntent().getStringExtra("city");
        position = getIntent().getStringExtra("position");
        min = getIntent().getStringExtra("min");
        max = getIntent().getStringExtra("max");
        uniqid = getIntent().getStringExtra("uniqid");
        photo = getIntent().getStringExtra("photo");

        //Toast.makeText(ContactDetailsActivity.this,photo,Toast.LENGTH_SHORT).show();

        if (position.equals("0") || position.equals("1")){
            bedrooms = getIntent().getStringExtra("bedrooms");
        }
        if (position.equals("2")){
            acres = getIntent().getStringExtra("acres");
        }
        if (position.equals("3")){
            sqft = getIntent().getStringExtra("sqft");
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        phone = user.getPhoneNumber();

        userName_et = (EditText) findViewById(R.id.username_et);
        userEmail_et = (EditText) findViewById(R.id.email_et);
        makeRequest_btn = (Button) findViewById(R.id.makeRequest_btn);

        reference = FirebaseDatabase.getInstance().getReference();

        makeRequest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName_et.getText().toString();
                String email = userEmail_et.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                String dateStr = simpleDateFormat.format(date);

                if (name.isEmpty() || email.isEmpty()){
                    Snackbar.make(view,"Please enter proper information",Snackbar.LENGTH_SHORT).show();
                }else {
                    final android.app.AlertDialog waitingDialog = new SpotsDialog(ContactDetailsActivity.this);
                    waitingDialog.show();
                 //   if (uniqid.equals("Buyer")){

                        if (position.equals("0")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"Apartment",bedrooms+" bedrooms",min,max,dateStr,phone,photo);
                            reference.child(uniqid).push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });
                        }
                        if (position.equals("1")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"House/Villa",bedrooms+" bedrooms",min,max,dateStr,phone,photo);
                            reference.child(uniqid).push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });;
                        }
                        if (position.equals("2")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"Land",acres+" acres",min,max,dateStr,phone,photo);
                            reference.child(uniqid).push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });;
                        }
                        if (position.equals("3")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"Shop",sqft+" sqft",min,max,dateStr,phone,photo);
                            reference.child(uniqid).push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });;
                        }

                  //  }
                 /*   if (uniqid.equals("Seller")){

                        if (position.equals("0")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"Apartment",bedrooms+" bedrooms",min,max,dateStr);
                            reference.child("Seller").push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });
                        }
                        if (position.equals("1")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"House/Villa",bedrooms+" bedrooms",min,max,dateStr);
                            reference.child("Seller").push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });;
                        }
                        if (position.equals("2")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"Land",acres+" acres",min,max,dateStr);
                            reference.child("Seller").push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });;
                        }
                        if (position.equals("3")){
                            Buyer buyer = new Buyer(name,email,area+","+city,"Shop",sqft+" sqft",min,max,dateStr);
                            reference.child("Seller").push().setValue(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    waitingDialog.dismiss();
                                    openDialog();
                                }
                            });;
                        }
                    } */
                }
            }
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailsActivity.this);
        builder.setTitle("Your request is submitted");
        builder.setMessage("Our representative will reach you as we found what you want.\nThank you.");


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ContactDetailsActivity.this,OptionActivity.class);
                startActivity(intent);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
