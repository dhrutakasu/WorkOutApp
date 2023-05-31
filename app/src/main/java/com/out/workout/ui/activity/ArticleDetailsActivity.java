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
import com.out.workout.model.ArticleDetailModel;
import com.out.workout.model.DetailModel;
import com.out.workout.model.SubCategories;
import com.out.workout.ui.adapter.ArticleDetailAdapter;
import com.out.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ArticleDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvArticleDescr;

    private String DietName, DietSlug;

    private Gson create;
    private ArrayList<DetailModel> categories = new ArrayList<>();
    private RecyclerView RvArticleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvArticleDescr = (TextView) findViewById(R.id.TvArticleDescr);
        RvArticleDetails = (RecyclerView) findViewById(R.id.RvArticleDetails);
    }

    private void initIntents() {
        DietName = getIntent().getStringExtra(Constants.DIET_NAME);
        DietSlug = getIntent().getStringExtra(Constants.DIET_SLUG);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {
        create = new GsonBuilder().create();
        AssetManager assets = context.getAssets();
        String AssetsFile = readAssetsFile(assets, "Diet/" + DietSlug + ".json");
        try {
            ArrayList<DetailModel> models = new ArrayList<>();
            JSONObject mJsonObject = new JSONObject(AssetsFile);
//            for (int i = 0; i < mJsonArray.length(); i++) {
//                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
            if (mJsonObject.getString("description").equalsIgnoreCase("")) {
                TvArticleDescr.setVisibility(View.GONE);
            } else {
                TvArticleDescr.setText(mJsonObject.getString("description"));
            }
            TvTitle.setText(mJsonObject.getString("title"));

            JSONArray jsonArray = new JSONArray(mJsonObject.getString("details"));
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                DetailModel detailModel1 = new DetailModel();
                detailModel1.setHeader(jsonObject.getString("header"));
                detailModel1.setFullDescription(jsonObject.getString("full_description"));
                detailModel1.setImage(jsonObject.getString("image"));
                models.add(detailModel1);
            }
            categories.addAll(models);
//            }
        } catch (JSONException e) {
            System.out.println("-------- detail : " + e.getMessage());
            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
        }
        RvArticleDetails.setLayoutManager(new LinearLayoutManager(context));
        RvArticleDetails.setAdapter(new ArticleDetailAdapter(context, categories));
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