package com.marvedie.redskyshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.marvedie.redskyshop.LoginActivity;
import com.marvedie.redskyshop.R;
import com.marvedie.redskyshop.RegisterActivity;


public class NotificationsFragment extends Fragment {

    //Define Views
    Button btnLogin;
    Button btnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_notifications, container, false);

            btnLogin = view.findViewById(R.id.btnLogin);
            btnRegister = view.findViewById(R.id.btnLogin);


            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent(getActivity(), LoginActivity.class));
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getActivity(), RegisterActivity.class));
                }
            });
            return view;
        }

    }
