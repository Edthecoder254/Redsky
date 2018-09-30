package com.marvedie.redskyshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.marvedie.redskyshop.AdminUpload;
import com.marvedie.redskyshop.Profile;
import com.marvedie.redskyshop.R;


public class ProfileFragment extends Fragment {

    Button AdminUpload;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();


        AdminUpload = view.findViewById(R.id.btnAdminUpload);

        AdminUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent(getActivity(), com.marvedie.redskyshop.AdminUpload.class));
            }
        });

        if (firebaseAuth.getCurrentUser() != null){
            AdminUpload.setEnabled(true);

        }


        return view;
    }
}
