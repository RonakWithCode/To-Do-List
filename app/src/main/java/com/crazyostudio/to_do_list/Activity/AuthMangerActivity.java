package com.crazyostudio.to_do_list.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.crazyostudio.to_do_list.Classes.keys;
import com.crazyostudio.to_do_list.Fragment.SiginFragment;
import com.crazyostudio.to_do_list.databinding.ActivityAuthMangerBinding;

public class AuthMangerActivity extends AppCompatActivity {
    ActivityAuthMangerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthMangerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Fragment SiginFragment = new SiginFragment();
        String goTo;
        if (getIntent()!=null){
            goTo = getIntent().getStringExtra(keys.GO_TO);
            Bundle bundle = new Bundle();
            bundle.putString(keys.GO_TO,goTo);
            SiginFragment.setArguments(bundle);
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString(keys.GO_TO,"main");
            SiginFragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(binding.FrameLayout.getId(),SiginFragment,"SiginFragment");
        transaction.addToBackStack("SiginFragment");
        transaction.commit();
    }
}