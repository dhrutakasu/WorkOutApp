package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.out.workout.R;
import com.out.workout.model.SubCategories;
import com.out.workout.ui.adapter.SubDietTipsAdapter;
import com.out.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CategoryItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private String DietName, DietSlug, DietImg;
    private Gson create;
    private ArrayList<SubCategories> categories = new ArrayList<>();
    private RecyclerView rvNutrients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_details);


        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        rvNutrients = (RecyclerView) findViewById(R.id.rvNutrients);
    }

    private void initIntents() {
        DietName = getIntent().getStringExtra(Constants.DIET_NAME);
        DietSlug = getIntent().getStringExtra(Constants.DIET_SLUG);
        DietImg = getIntent().getStringExtra(Constants.DIET_IMG);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(DietName);
        create = new GsonBuilder().create();
        AssetManager assets = context.getAssets();
        System.out.println("---- dNAme  : " + DietName);
        System.out.println("---- dSlug : " + DietSlug);
        String AssetsFile = readAssetsFile(assets, "Diet/" + DietSlug + ".json");
        System.out.println("----- ASSSS : " + AssetsFile);
        try {
            JSONArray mJsonArray = new JSONArray(AssetsFile);
            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                SubCategories subCategories = new SubCategories();
                subCategories.setIcon(mJsonObject.getString("icon"));
                subCategories.setSingleItem(mJsonObject.getBoolean("isSingleItem"));
                subCategories.setName(mJsonObject.getString("name"));
                subCategories.setSlug(mJsonObject.getString("slug"));
                subCategories.setLayoutType(mJsonObject.getInt("layout_type"));
                categories.add(subCategories);
            }
        } catch (JSONException e) {
            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
        }

        rvNutrients.setLayoutManager(new LinearLayoutManager(context));
//        rvNutrients.setAdapter(new SubDietTipsAdapter(context, categories, DietName));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
        }
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