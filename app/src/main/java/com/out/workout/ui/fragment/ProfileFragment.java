package com.out.workout.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.out.workout.R;

public class ProfileFragment extends Fragment {


    private View ProfileView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileView = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews();
        initListeners();
        initActions();
        return ProfileView;
    }

    private void initViews() {
        if (ProfileView!=null){

        }
    }

    private void initListeners() {

    }

    private void initActions() {

    }
}