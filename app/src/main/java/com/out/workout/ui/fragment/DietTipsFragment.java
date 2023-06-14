package com.out.workout.ui.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.R;
import com.out.workout.model.Data;
import com.out.workout.model.DietTipsCategory;
import com.out.workout.ui.adapter.DietTipsAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DietTipsFragment extends Fragment {


    private View DietView;
    private RecyclerView RvDiets;
    private Gson create;
    private ArrayList<DietTipsCategory> categories = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DietView = inflater.inflate(R.layout.fragment_diet_tips, container, false);
        initViews();
        initListeners();
        initActions();
        return DietView;
    }

    private void initViews() {
        if (DietView != null) {
            RvDiets = (RecyclerView) DietView.findViewById(R.id.RvDiets);
        }
    }

    private void initListeners() {

    }

    private void initActions() {
        create = new GsonBuilder().create();
        AssetManager assets = getContext().getAssets();
        String AssetsFile = readAssetsFile(assets, "DietFile.json");
        create.fromJson(AssetsFile, Data.class);
        categories.addAll(((Data) create.fromJson(AssetsFile, Data.class)).getCategories());

        RvDiets.setLayoutManager(new LinearLayoutManager(getContext()));
        RvDiets.setAdapter(new DietTipsAdapter(getContext(), categories));
    }

    public final String readAssetsFile(AssetManager assetManager, String str) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = assetManager.open(str, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }
}