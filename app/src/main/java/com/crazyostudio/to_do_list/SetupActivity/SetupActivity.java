package com.crazyostudio.to_do_list.SetupActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.crazyostudio.to_do_list.MainActivity;
import com.crazyostudio.to_do_list.databinding.ActivitySetupBinding;

public class SetupActivity extends AppCompatActivity {
    ActivitySetupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("FirstTime",MODE_PRIVATE);
        if (preferences.getString("IsOpenBefore","").equals("yes")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        binding.Continue.setOnClickListener(view -> {
            if (binding.checkboxTerms.isChecked()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("IsOpenBefore","yes");
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        });


    }
}