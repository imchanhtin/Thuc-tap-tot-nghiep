package com.imchanhtin.canhbaochay;


import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;


import com.google.firebase.database.*;

import pl.droidsonroids.gif.GifImageView;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {
    DatabaseReference nhietdo, doam, gas, co, khoi;
    TextView textNhiet, textDoam, textGas, textCO, textKhoi, textTinhtrang;
    double gtKhoi, gtCO, gtGAS, gtDOAM, gtNhiet;
    GifImageView anh;

    MediaPlayer chay, nguyhiem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textNhiet = findViewById(R.id.textNhiet);
        textDoam = findViewById(R.id.textDoam);
        textGas = findViewById(R.id.textGas);
        textCO = findViewById(R.id.textCO);
        textKhoi = findViewById(R.id.textKhoi);
        textTinhtrang = findViewById(R.id.textTR);

        chay = MediaPlayer.create(this, R.raw.chay);
        nguyhiem = MediaPlayer.create(this, R.raw.warning);


        //đọc dữ liệu theo node trong Firebase (cái này lấy từ nút canhbaochay/nhietdo)
        nhietdo = FirebaseDatabase.getInstance().getReference().child("nhietdo");
        doam = FirebaseDatabase.getInstance().getReference().child("doam");
        gas = FirebaseDatabase.getInstance().getReference().child("gas");
        co = FirebaseDatabase.getInstance().getReference().child("CO");
        khoi = FirebaseDatabase.getInstance().getReference().child("khoi");
        readDB();
    }

    public void readDB() {
        nhietdo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double value = dataSnapshot.getValue(Double.class);
                gtNhiet = value;
                Log.v(TAG, "Value is: " + value);
                textNhiet.setText(value + "");
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
                textDoam.setText(gtDOAM + "");
                tinhtrang();
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
                textGas.setText(gtGAS + "");
                tinhtrang();
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
                textCO.setText(gtCO + "");
                tinhtrang();
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
                textKhoi.setText(value + "");
                tinhtrang();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.v(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void tinhtrang() {

        if (gtNhiet <= 27 && gtCO <20) {
            textTinhtrang.setText("Bình thường");
            textTinhtrang.setTextColor(Color.GREEN);
            mau(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
            anhtinhtrang(R.drawable.ok);
            if(nguyhiem.isLooping()){
                nguyhiem.stop();
                nguyhiem = MediaPlayer.create(this,R.raw.warning);
            }
            if(chay.isLooping()){
                chay.stop();
                chay = MediaPlayer.create(this,R.raw.chay);
            }
        } else if (gtNhiet > 36 && gtKhoi >= 10 && gtCO >= 10 || gtNhiet > 50) {
            textTinhtrang.setText("Cháy");
            textTinhtrang.setTextColor(Color.RED);
            mau(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED);
            anhtinhtrang(R.drawable.alert);
            chay.start();
            chay.setLooping(true);
            if(nguyhiem.isLooping()){
                nguyhiem.setLooping(false);
                nguyhiem.stop();
                nguyhiem = MediaPlayer.create(this,R.raw.warning);
            }
        } else if (gtNhiet >= 36 && gtNhiet <= 50) {
            textTinhtrang.setText("Nhiệt độ tăng cao");
            textTinhtrang.setTextColor(Color.YELLOW);
            mau(Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, Color.GREEN);
            anhtinhtrang(R.drawable.nguyhiem);
            nguyhiem.start();
            nguyhiem.setLooping(true);
            if(chay.isLooping()){
                chay.setLooping(false);
                chay.stop();
                chay = MediaPlayer.create(this,R.raw.chay);
            }
        } else if (gtDOAM > 95 ) {
            textTinhtrang.setText("Độ ẩm cao");
            textTinhtrang.setTextColor(Color.YELLOW);
            mau(Color.GREEN, Color.RED, Color.GREEN, Color.GREEN, Color.GREEN);
            anhtinhtrang(R.drawable.nguyhiem);
            nguyhiem.start();
            nguyhiem.setLooping(true);
            if(chay.isLooping()){
                chay.setLooping(false);
                chay.stop();
                chay = MediaPlayer.create(this,R.raw.chay);
            }
        } else if (gtGAS >= 15 && gtCO >= 20) {
            textTinhtrang.setText("Rò rỉ khí GAS!");
            textTinhtrang.setTextColor(Color.RED);
            mau(Color.GREEN, Color.GREEN, Color.RED, Color.RED, Color.RED);
            anhtinhtrang(R.drawable.nguyhiem);
            nguyhiem.start();
            nguyhiem.setLooping(true);
            if(chay.isLooping()){
                chay.setLooping(false);
                chay.stop();
                chay = MediaPlayer.create(this,R.raw.chay);
            }
        } else if (gtKhoi > 40) {
            textTinhtrang.setText("Nguy hiểm");
            textTinhtrang.setTextColor(Color.YELLOW);
            mau(Color.GREEN, Color.YELLOW, Color.GREEN, Color.YELLOW, Color.RED);
            anhtinhtrang(R.drawable.nguyhiem);
            nguyhiem.start();
            nguyhiem.setLooping(true);
            if(chay.isLooping()){
                chay.setLooping(false);
                chay.stop();
                chay = MediaPlayer.create(this,R.raw.chay);
            }
        }
    }
    public void mau(int NHIET, int DOAM, int GAS, int CO, int KHOI) {
        textNhiet.setTextColor(NHIET);
        textDoam.setTextColor(DOAM);
        textGas.setTextColor(GAS);
        textCO.setTextColor(CO);
        textKhoi.setTextColor(KHOI);
    }
    public void anhtinhtrang(int dcAnh) {
        anh = findViewById(R.id.anh);
        anh.setImageResource(dcAnh);
    }
}

