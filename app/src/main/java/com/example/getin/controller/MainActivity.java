package com.example.getin.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.getin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(this,"Firebase connected !",Toast.LENGTH_SHORT).show();
    }

    public void chooseAct(View v){
        startActivity(new Intent(this,ChooseAct.class));
    }
}
