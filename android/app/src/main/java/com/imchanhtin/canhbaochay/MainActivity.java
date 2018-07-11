package com.imchanhtin.canhbaochay;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.*;
import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {

    DatabaseReference nhietdo, doam, gas,co,khoi;
    TextView textNhiet, textDoam, textGas,textCO,textKhoi, textTinhtrang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textNhiet=findViewById(R.id.textNhiet);
        textDoam=findViewById(R.id.textDoam);
        textGas=findViewById(R.id.textGas);
        textCO=findViewById(R.id.textCO);
        textKhoi=findViewById(R.id.textKhoi);
        textTinhtrang = findViewById(R.id.textTR);


        //đọc dữ liệu theo node trong Firebase (cái này lấy từ nút canhbaochay/nhietdo)
        nhietdo = FirebaseDatabase.getInstance().getReference().child("nhietdo");
        doam = FirebaseDatabase.getInstance().getReference().child("doam");
        gas = FirebaseDatabase.getInstance().getReference().child("gas");
        co = FirebaseDatabase.getInstance().getReference().child("CO");
        khoi = FirebaseDatabase.getInstance().getReference().child("khoi");
        readDB();

    }
  public void readDB(){
        nhietdo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(Double.class)+"";
                Log.v(TAG, "Value is: " + value);
                textNhiet.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.v(TAG, "Failed to read value.", error.toException());
            }
        });
        doam.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              String value = dataSnapshot.getValue(Double.class)+"";
              Log.v(TAG, "Value is: " + value);
              textDoam.setText(value);
          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              Log.v(TAG, "Failed to read value.", error.toException());
          }
      });
        gas.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              String value = dataSnapshot.getValue(Double.class)+"";
              Log.v(TAG, "Value is: " + value);
              textGas.setText(value);
          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              Log.v(TAG, "Failed to read value.", error.toException());
          }
      });
        co.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              String value = dataSnapshot.getValue(Double.class)+"";
              Log.v(TAG, "Value is: " + value);
              textCO.setText(value);
          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              Log.v(TAG, "Failed to read value.", error.toException());
          }
      });
        khoi.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              Double value = dataSnapshot.getValue(Double.class);
              Log.v(TAG, "Value is: " + value);
              textKhoi.setText(value.toString());
          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              Log.v(TAG, "Failed to read value.", error.toException());
          }
      });
  }

}
