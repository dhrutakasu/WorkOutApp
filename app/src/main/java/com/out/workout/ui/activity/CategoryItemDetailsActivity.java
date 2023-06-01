package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.out.workout.R;
import com.out.workout.model.NutritionalContent;
import com.out.workout.model.ValueModel;
import com.out.workout.model.subDivModel;
import com.out.workout.ui.adapter.NutrientAdapter;
import com.out.workout.ui.adapter.PropsAdapter;
import com.out.workout.ui.adapter.SubDivAdapter;
import com.out.workout.ui.adapter.VitaminsAdapter;
import com.out.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CategoryItemDetailsActivity extends AppCompatActivity implements View.OnClickListener, NutrientAdapter.setClickItem {

    private Context context;
    private ImageView IvBack;
    private String DietName, DietSlug, DietImg;
    private Gson create;
    private RecyclerView RvItemNutrients, RvItemProteins, RvItemVitamins, RvItemMinerals, RvItemPros, RvItemCons;
    private TextView TvItemNutritionalTitle, TvItemNutritionalServing, TvItemSubDivTitle, TvItemVitaminsTitle, TvItemMineralsTitle;
    private ImageView IvItemImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_details);


        initViews();
        initIntents();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        RvItemNutrients = (RecyclerView) findViewById(R.id.RvItemNutrients);
        RvItemProteins = (RecyclerView) findViewById(R.id.RvItemProteins);
        RvItemVitamins = (RecyclerView) findViewById(R.id.RvItemVitamins);
        RvItemMinerals = (RecyclerView) findViewById(R.id.RvItemMinerals);
        RvItemPros = (RecyclerView) findViewById(R.id.RvItemPros);
        RvItemCons = (RecyclerView) findViewById(R.id.RvItemCons);
        IvItemImg = (ImageView) findViewById(R.id.IvItemImg);
        TvItemNutritionalTitle = (TextView) findViewById(R.id.TvItemNutritionalTitle);
        TvItemNutritionalServing = (TextView) findViewById(R.id.TvItemNutritionalServing);
        TvItemSubDivTitle = (TextView) findViewById(R.id.TvItemSubDivTitle);
        TvItemVitaminsTitle = (TextView) findViewById(R.id.TvItemVitaminsTitle);
        TvItemMineralsTitle = (TextView) findViewById(R.id.TvItemMineralsTitle);
    }

    private void initIntents() {
        DietName = getIntent().getStringExtra(Constants.DIET_NAME);
        DietSlug = getIntent().getStringExtra(Constants.DIET_SLUG);
        DietImg = getIntent().getStringExtra(Constants.DIET_IMG);
    }

    private void initActions() {
        IvBack.setOnClickListener(v -> onBackPressed());
        try {
            InputStream ims = context.getAssets().open("DietImg/" + DietImg);
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            IvItemImg.setImageBitmap(bitmap);
            ims.close();
        } catch (IOException ex) {
            IvItemImg.setVisibility(View.GONE);
            System.out.println("----- catch : " + ex.getMessage());
        }
        create = new GsonBuilder().create();
        AssetManager assets = context.getAssets();
        String AssetsFile = readFile(assets, "Diet/" + DietSlug + ".json");
        try {
            JSONObject jsonObject = new JSONObject(AssetsFile);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("nutrients"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject Detail = jsonArray.getJSONObject(i);
                if (Detail.getString("heading").equalsIgnoreCase("Nutrients")) {
                    JSONArray array = new JSONArray(Detail.getString("values"));
                    List<ValueModel> valueModels = new ArrayList<>();
                    List<subDivModel> subDivModels = new ArrayList<>();
                    for (int j = 1; j < array.length(); j++) {
                        ValueModel valueModel = new ValueModel();
                        JSONObject DetailObject = array.getJSONObject(j);
                        valueModel.setAmount(DetailObject.getString("amount"));
                        subDivModels = new ArrayList<>();
                        if (DetailObject.has("subDiv")) {
                            JSONArray subDiv = new JSONArray(DetailObject.getString("subDiv"));
                            for (int k = 0; k < subDiv.length(); k++) {
                                JSONObject subDivObject = subDiv.getJSONObject(k);
                                subDivModel subDivModel = new subDivModel();
                                subDivModel.setAmount(subDivObject.getString("amount"));
                                subDivModel.setName(subDivObject.getString("name"));
                                subDivModels.add(subDivModel);
                            }
                        }
                        valueModel.setSubDiv(subDivModels);
                        valueModel.setName(DetailObject.getString("name"));
                        valueModels.add(valueModel);
                    }
                    RvItemNutrients.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    RvItemNutrients.setAdapter(new NutrientAdapter(context, valueModels, subDivModels, DietName, this));
                } else if (Detail.getString("heading").equalsIgnoreCase("Vitamins")) {
                    TvItemVitaminsTitle.setText(Detail.getString("heading"));
                    JSONArray array = new JSONArray(Detail.getString("values"));
                    List<ValueModel> valueModels = new ArrayList<>();
                    for (int j = 0; j < array.length(); j++) {
                        ValueModel valueModel = new ValueModel();
                        JSONObject DetailObject = array.getJSONObject(j);
                        valueModel.setAmount(DetailObject.getString("amount"));
                        if (DetailObject.has("daily_value")) {
                            valueModel.setDailyValue(DetailObject.getString("daily_value"));
                        }
                        valueModel.setName(DetailObject.getString("name"));
                        valueModels.add(valueModel);
                    }
                    RvItemVitamins.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    RvItemVitamins.setAdapter(new VitaminsAdapter(context, valueModels));
                } else if (Detail.getString("heading").equalsIgnoreCase("Minerals")) {
                    TvItemMineralsTitle.setText(Detail.getString("heading"));
                    JSONArray array = new JSONArray(Detail.getString("values"));
                    List<ValueModel> valueModels = new ArrayList<>();
                    for (int j = 0; j < array.length(); j++) {
                        ValueModel valueModel = new ValueModel();
                        JSONObject DetailObject = array.getJSONObject(j);
                        valueModel.setAmount(DetailObject.getString("amount"));
                        if (DetailObject.has("daily_value")) {
                            valueModel.setDailyValue(DetailObject.getString("daily_value").isEmpty() ? "" : DetailObject.getString("daily_value"));
                        }
                        valueModel.setName(DetailObject.getString("name"));
                        valueModels.add(valueModel);
                    }
                    RvItemMinerals.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    RvItemMinerals.setAdapter(new VitaminsAdapter(context, valueModels));
                }
            }

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

            RvItemPros.setLayoutManager(new LinearLayoutManager(context));
            RvItemPros.setAdapter(new PropsAdapter(context, ProsArrayList));

            JSONArray cons = jsonObject.getJSONArray("cons");
            ArrayList<String> consArrayList = new ArrayList<>();
            for (int i = 0; i < cons.length(); i++) {
                consArrayList.add(cons.getString(i).toString());
            }

            RvItemCons.setLayoutManager(new LinearLayoutManager(context));
            RvItemCons.setAdapter(new PropsAdapter(context, consArrayList));

            String serving = jsonObject.getString("serving");
            TvItemNutritionalTitle.setText(serving.substring(0, serving.indexOf("per ")));
            TvItemNutritionalServing.setText(serving.substring(serving.indexOf("per ") + 4));

        } catch (JSONException e) {
            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public final String readFile(AssetManager manager, String str) {
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = manager.open(str, Context.MODE_WORLD_READABLE);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (inputStream != null)
                    inputStream.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return builder.toString();
    }

    @Override
    public void onClick(List<subDivModel> divModels, String name) {
        if (RvItemProteins.getVisibility() == View.GONE) {
            TvItemSubDivTitle.setVisibility(View.VISIBLE);
            RvItemProteins.setVisibility(View.VISIBLE);
            TvItemSubDivTitle.setText(name);
            RvItemProteins.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            RvItemProteins.setAdapter(new SubDivAdapter(context, divModels));
        } else {
            TvItemSubDivTitle.setVisibility(View.GONE);
            RvItemProteins.setVisibility(View.GONE);
        }
    }
}