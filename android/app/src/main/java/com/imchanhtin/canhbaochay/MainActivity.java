package com.imchanhtin.canhbaochay;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.*;
import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {
    DatabaseReference nhietdo, doam, gas,co,khoi;
    TextView textNhiet, textDoam, textGas,textCO,textKhoi, textTinhtrang;
    double gtKhoi,gtCO,gtGAS, gtDOAM, gtNhiet;
    int dem=0;

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
        tinhtrang();

    }
  public void readDB(){

        nhietdo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double value = dataSnapshot.getValue(Double.class);
                gtNhiet = value;
                Log.v(TAG, "Value is: " + value);
                textNhiet.setText(value+"");
                dem++;
                tinhtrang();

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
              gtDOAM = dataSnapshot.getValue(Double.class);
              Log.v(TAG, "Value is: " + gtDOAM);
              textDoam.setText(gtDOAM+"");
              dem++;

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
              gtGAS = dataSnapshot.getValue(Double.class);
              Log.v(TAG, "Value is: " + gtGAS);
              textGas.setText(gtGAS+"");
              dem++;

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
              gtCO = dataSnapshot.getValue(Double.class);
              Log.v(TAG, "Value is: " + gtCO);
              textCO.setText(gtCO+"");
              dem++;

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
              double value = dataSnapshot.getValue(Double.class);
              gtKhoi = value;
              Log.v(TAG, "Value is: " + value);
              textKhoi.setText(value+"");
              dem++;

          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              Log.v(TAG, "Failed to read value.", error.toException());
          }
      });

  }

  public void tinhtrang(){
      if(gtNhiet <=27) {
          textTinhtrang.setText("Bình thường");
          textTinhtrang.setTextColor(Color.GREEN);
          mau(Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN);
      }
      else if(gtNhiet > 36 && gtKhoi >= 10 && gtCO >= 10 || gtNhiet >50){
          textTinhtrang.setText("Cháy");
          textTinhtrang.setTextColor(Color.RED);
          mau(Color.RED,Color.RED,Color.RED,Color.RED,Color.RED);
      }
      else if(gtNhiet >= 36 && gtNhiet <= 50) {
          textTinhtrang.setText("Nhiệt độ tăng cao");
          textTinhtrang.setTextColor(Color.YELLOW);
          mau(Color.YELLOW,Color.YELLOW,Color.GREEN,Color.GREEN,Color.GREEN);
      }
      else if(gtDOAM > 95){
          textTinhtrang.setText("Độ ẩm cao");
          textTinhtrang.setTextColor(Color.YELLOW);
          mau(Color.GREEN,Color.RED,Color.GREEN,Color.GREEN,Color.GREEN);
      }
      else if (gtGAS >= 15 && gtCO >= 20) {
          textTinhtrang.setText("Rò rỉ khí GAS!");
          textTinhtrang.setTextColor(Color.YELLOW);
          mau(Color.GREEN,Color.GREEN,Color.RED,Color.RED,Color.RED);
      }
    }
    public void mau(int NHIET, int DOAM, int GAS, int CO, int KHOI){
//        TextView txtNhiet = findViewById(R.id.nhietdo);
//        TextView txtdoam = findViewById(R.id.doam);
//        TextView txtgas = findViewById(R.id.gas);
//        TextView txtco = findViewById(R.id.co);
//        TextView txtkhoi = findViewById(R.id.khoi);
//
//        txtNhiet.setTextColor(NHIET);
//        txtdoam.setTextColor(DOAM);
//        txtco.setTextColor(CO);
//        txtkhoi.setTextColor(KHOI);
//        txtgas.setTextColor(GAS);

        textNhiet.setTextColor(NHIET);
        textDoam.setTextColor(DOAM);
        textGas.setTextColor(GAS);
        textCO.setTextColor(CO);
        textKhoi.setTextColor(KHOI);
    }
}

