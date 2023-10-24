package com.crazyostudio.to_do_list.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.FragmentMoreBinding;
import com.google.firebase.auth.FirebaseAuth;


public class MoreFragment extends Fragment {
    FragmentMoreBinding binding;
    FirebaseAuth auth;


    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            binding.relativeNotAuth.setVisibility(View.VISIBLE);
        } else {
            binding.mainLayout.setVisibility(View.VISIBLE);
            binding.Username.setText(auth.getCurrentUser().getDisplayName());
            if (auth.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(requireContext()).load(auth.getCurrentUser().getPhotoUrl()).placeholder(R.drawable.userimage).into(binding.userImage);
            }
        }
        return binding.getRoot();
    }
}

