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
import com.out.workout.model.ItemDetailModel;
import com.out.workout.model.NutrientModel;
import com.out.workout.model.NutritionalContent;
import com.out.workout.model.SubCategories;
import com.out.workout.model.ValueModel;
import com.out.workout.ui.adapter.NutrientAdapter;
import com.out.workout.ui.adapter.SubDietTipsAdapter;
import com.out.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CategoryItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
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
        rvNutrients = (RecyclerView) findViewById(R.id.rvNutrients);
    }

    private void initIntents() {
        DietName = getIntent().getStringExtra(Constants.DIET_NAME);
        DietSlug = getIntent().getStringExtra(Constants.DIET_SLUG);
        DietImg = getIntent().getStringExtra(Constants.DIET_IMG);
    }

    private void initListeners() {
    }

    private void initActions() {
        create = new GsonBuilder().create();
        AssetManager assets = context.getAssets();
        System.out.println("---- dNAme  : " + DietName);
        System.out.println("---- dSlug : " + DietSlug);
        String AssetsFile = readAssetsFile(assets, "Diet/" + DietSlug + ".json");
        System.out.println("----- ASSSS : " + AssetsFile);
        try {
            JSONObject jsonObject = new JSONObject(AssetsFile);
            ItemDetailModel itemDetailModel = new ItemDetailModel();
            JSONArray jsonArray = new JSONArray(jsonObject.getString("nutrients"));
            List<NutrientModel> nutrientModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject Detail = jsonArray.getJSONObject(i);
                NutrientModel model = new NutrientModel();
                model.setHeading(Detail.getString("heading"));
                model.setNoOfColumn(Detail.optInt("noOfColumn"));

                JSONArray array = new JSONArray(Detail.getString("values"));
                System.out.println("----- dNAme ; "+array.length());
                List<ValueModel> valueModels = new ArrayList<>();
                for (int j = 0; j < array.length(); j++) {
                    ValueModel valueModel = new ValueModel();
                    JSONObject DetailObject = jsonArray.getJSONObject(i);
                    valueModel.setAmount(DetailObject.getString("amount"));
                    valueModel.setDailyValue(DetailObject.getString("daily_value").isEmpty() ? "" : DetailObject.getString("daily_value"));
                    valueModel.setSubDiv(DetailObject.getString("subDiv").isEmpty() ? "" : DetailObject.getString("daily_value"));
                    valueModel.setName(DetailObject.getString("name"));
                    valueModels.add(valueModel);
                }
                rvNutrients.setLayoutManager(new LinearLayoutManager(context));
                rvNutrients.setAdapter(new NutrientAdapter(context, valueModels, DietName));
                model.setValues(valueModels);
                nutrientModels.add(model);
            }
            itemDetailModel.setNutrients(nutrientModels);


            JSONArray nutrients = new JSONArray(jsonObject.getString("nutritionalContent"));
            List<NutritionalContent> nutritionalContentList = new ArrayList<>();
            for (int i = 0; i < nutrients.length(); i++) {
                JSONObject DetailObject = nutrients.getJSONObject(i);
                NutritionalContent nutritionalContent = new NutritionalContent();
                nutritionalContent.setValue(DetailObject.getString("value"));
                nutritionalContent.setName(DetailObject.getString("name"));
                nutritionalContentList.add(nutritionalContent);
            }
            JSONArray arrays = jsonObject.getJSONArray("pros");
            ArrayList<String> ProsArrayList = new ArrayList<>();
            for (int i = 0; i < arrays.length(); i++) {
                ProsArrayList.add(arrays.getString(i).toString());
            }
            String serving = jsonObject.getString("serving");

        } catch (JSONException e) {
            System.out.println("------- ex dNAme : "+e.getMessage());
            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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