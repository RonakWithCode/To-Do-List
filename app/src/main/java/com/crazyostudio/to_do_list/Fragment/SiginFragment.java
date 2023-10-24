package com.crazyostudio.to_do_list.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.crazyostudio.to_do_list.Classes.keys;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.FragmentSiginBinding;
import com.google.firebase.FirebaseApp;

public class SiginFragment extends Fragment {
    FragmentSiginBinding binding;
    String go_To;


    public SiginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            go_To = getArguments().getString(keys.GO_TO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSiginBinding.inflate(inflater,container,false);
        FirebaseApp.initializeApp(requireContext());

        binding.button.setOnClickListener(sigin->{
            if (binding.checkBox.isChecked()){
                Bundle bundle = new Bundle();
                bundle.putString(keys.NUMBER, binding.Number.getText().toString());
                bundle.putString(keys.GO_TO,go_To);
                Fragment fragment = new AuthOTP();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragment.setArguments(bundle);
                transaction.replace(R.id.FrameLayout,fragment,"AuthOTP");
                transaction.addToBackStack("AuthOTP");
                transaction.commit();

            }
        });


        return binding.getRoot();
    }
}