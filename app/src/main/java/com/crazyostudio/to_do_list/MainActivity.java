package com.crazyostudio.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.crazyostudio.to_do_list.Fragment.CalendarFragment;
import com.crazyostudio.to_do_list.Fragment.MoreFragment;
import com.crazyostudio.to_do_list.Fragment.TaskFragment;
import com.crazyostudio.to_do_list.databinding.ActivityMainBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReplaceFragment(new TaskFragment());

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i){
                    case 0:
                        ReplaceFragment(new TaskFragment());
                        break;
                    case 1:
                        ReplaceFragment(new CalendarFragment());

                        break;
                    case 2:
                        ReplaceFragment(new MoreFragment());
                        break;
                }
                return true;
            }


        });
    }
    private void ReplaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.FrameLayout.getId(),fragment);
        transaction.commit();
    }
}