package com.crazyostudio.to_do_list.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crazyostudio.to_do_list.Activity.FragmentLoder;
import com.crazyostudio.to_do_list.Classes.basicFun;
import com.crazyostudio.to_do_list.Classes.keys;
import com.crazyostudio.to_do_list.MainActivity;
import com.crazyostudio.to_do_list.Model.UserinfoModels;
import com.crazyostudio.to_do_list.databinding.FragmentGetAuthUserDetailsBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class GetAuthUserDetailsFragment extends Fragment {
    FragmentGetAuthUserDetailsBinding binding;
    FirebaseDatabase db;
    private String number;
    private String go_to;
    private Uri userImage;
    private boolean IsUseImage;

    public GetAuthUserDetailsFragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getString(keys.NUMBER);
            go_to = getArguments().getString(keys.GO_TO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetAuthUserDetailsBinding.inflate(inflater,container,false);
        db  = FirebaseDatabase.getInstance();

        binding.number.setText(number);
        binding.userImage.setOnClickListener(view -> ImagePicker.with(requireActivity())
                .crop()
                .compress(1024)
                .maxResultSize(800, 800)
                .start(123));
        binding.signup.setOnClickListener(onclick->{
            if (basicFun.CheckField(binding.Mail)&& basicFun.CheckField(binding.Name)){
                ProgressDialog p = new ProgressDialog(requireContext());
                p.setTitle("Waiting.....");
                p.show();
                UserinfoModels UserinfoModels;
                if (IsUseImage){
                    UserinfoModels = new UserinfoModels(FirebaseAuth.getInstance().getUid(), binding.Name.getText().toString(),binding.Mail.getText().toString(),userImage.toString(),number,true);
                    db.getReference().child("UserInfo").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(UserinfoModels).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(binding.Name.getText().toString()).setPhotoUri(userImage)
                                    .build();
                            assert user != null;
                            user.updateProfile(profileUpdates).addOnSuccessListener(unused -> {
                                if (go_to.equals("main")){
                                    startActivity(new Intent(requireContext(), MainActivity.class));

                                }else {
                                    Intent intent = new Intent(requireContext(), FragmentLoder.class);
                                    intent.putExtra(keys.GO_TO,go_to);
                                    startActivity(intent);
                                }
                            });
                        }
                        else {
                            basicFun.AlertDialog(requireContext(),task.toString());
                        }
                    }).addOnFailureListener(e -> basicFun.AlertDialog(requireContext(),e.toString()));
                }else {
                    UserinfoModels = new UserinfoModels(FirebaseAuth.getInstance().getUid(), binding.Name.getText().toString(),binding.Mail.getText().toString(),number,true);
                    db.getReference().child("UserInfo").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(UserinfoModels).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(binding.Name.getText().toString()).build();
                            assert user != null;
                            user.updateProfile(profileUpdates).addOnSuccessListener(unused -> {
                                if (go_to.equals("main")){
                                    startActivity(new Intent(requireContext(), MainActivity.class));
                                }else {
                                    Intent intent = new Intent(requireContext(), FragmentLoder.class);
                                    intent.putExtra(keys.GO_TO,go_to);
                                    startActivity(intent);
                                }
                            });

                        }else {
                            basicFun.AlertDialog(requireContext(),task.toString());
                        }

                    }).addOnFailureListener(e -> basicFun.AlertDialog(requireContext(),e.toString()));
                }
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (data.getData() != null && requestCode == 123) {
            userImage = data.getData();
            binding.userImage.setImageURI(data.getData());
            IsUseImage = true;
        }
    }
}