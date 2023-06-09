package com.out.workout.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdSize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;
import com.out.workout.model.DetailModel;
import com.out.workout.ui.adapter.ArticleDetailAdapter;
import com.out.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack, IvArticleImage;
    private TextView TvTitle, TvArticleDescr;

    private String DietName, DietSlug, DietImage;

    private Gson create;
    private ArrayList<DetailModel> categories = new ArrayList<>();
    private RecyclerView RvArticleDetails;
    private boolean isImage = false;

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
        IvArticleImage = (ImageView) findViewById(R.id.IvArticleImage);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvArticleDescr = (TextView) findViewById(R.id.TvArticleDescr);
        RvArticleDetails = (RecyclerView) findViewById(R.id.RvArticleDetails);
    }

    private void initIntents() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        DietName = getIntent().getStringExtra(Constants.DIET_NAME);
        DietSlug = getIntent().getStringExtra(Constants.DIET_SLUG);
        DietImage = getIntent().getStringExtra(Constants.DIET_IMG);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {
        Glide.with(context)
                .load("https://7starinnovation.com/workimg/" + DietImage)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error)
                .into(IvArticleImage);
//        try {
//            InputStream ims = context.getAssets().open("DietImg/" + DietImage);
//            Bitmap bitmap = BitmapFactory.decodeStream(ims);
//            IvArticleImage.setImageBitmap(bitmap);
//            IvArticleImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        } catch (IOException ex) {
//            IvArticleImage.setVisibility(View.GONE);
//        }
        create = new GsonBuilder().create();
        AssetManager assets = context.getAssets();
        String AssetsFile = readAssetsFile(assets, "Diet/" + DietSlug + ".json");
        try {
            ArrayList<DetailModel> models = new ArrayList<>();
            JSONObject mJsonObject = new JSONObject(AssetsFile);
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
        } catch (JSONException e) {
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

    @Override
    public void onBackPressed() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Load Ad....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Ad_Interstitial.getInstance().showInter(ArticleDetailsActivity.this, new Ad_Interstitial.MyCallback() {
                    @Override
                    public void callbackCall() {
                        finish();
                    }
                });
            }
        }, 3000L);
    }
}