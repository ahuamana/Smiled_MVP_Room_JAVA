package com.paparazziapps.mvp_smiled_java_room.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityActividadInfoBinding;

public class ActividadInfoActivity extends AppCompatActivity {

    ActivityActividadInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActividadInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //all code here
        showToolbar();


    }

    private void showToolbar() {

        binding.mytoolbar.imageVisibility.setVisibility(View.GONE);
        binding.mytoolbar.linearEditDelete.setVisibility(View.VISIBLE);

        setSupportActionBar(binding.mytoolbar.getRoot());
    }
}