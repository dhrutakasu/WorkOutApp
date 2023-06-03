package com.out.workout.ui.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.R;
import com.out.workout.model.ArticleModel;
import com.out.workout.ui.adapter.ArticleListAdapter;
import com.out.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private String DietName, DietSlug, DietImg;
    private Gson create;
    private ArrayList<ArticleModel> categories = new ArrayList<>();
    private RecyclerView RvArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        RvArticleList = (RecyclerView) findViewById(R.id.RvArticleList);
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
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.str_shortcut3));
        create = new GsonBuilder().create();
        AssetManager assets = context.getAssets();
        String AssetsFile = readAssetsFile(assets, "Diet/" + DietSlug + ".json");
        try {
            JSONArray mJsonArray = new JSONArray(AssetsFile);
            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                ArticleModel subCategories = new ArticleModel();
                subCategories.setIcon(mJsonObject.getString("icon"));
                subCategories.setShortDescription(mJsonObject.optString("short_description"));
                subCategories.setName(mJsonObject.getString("name"));
                subCategories.setSlug(mJsonObject.getString("slug"));
                subCategories.setLayoutType(mJsonObject.getInt("layout_type"));
                categories.add(subCategories);
            }
        } catch (JSONException e) {
        }

        RvArticleList.setLayoutManager(new LinearLayoutManager(context));
        RvArticleList.setAdapter(new ArticleListAdapter(context,categories));
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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
        }
    }
}