package com.raemacias.superheroes;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.like.LikeButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.HeroAdapter;
import models.Hero;
import network.Api;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This code was completed by following the tutorial at https://www.simplifiedcoding.net/expandable-recyclerview-android/

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    final String URL_GET_DATA = "https://simplifiedcoding.net/demos/marvel/";

    AdView mAdView;
    RecyclerView recyclerView;
    HeroAdapter adapter;
    List<Hero> heroList;

    private String TAG = "Main Activity";
    private String LOG = "";
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        heroList = new ArrayList<>();

        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        //To show banner ads
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //This is the test ads id
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //calling the method to display the heroes
//        getHeroes();
        loadHeroes();
        loadFavorites();

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.menu, menu);
//            return true;
        }


//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//
//                final Hero deleteFavoriteItems = new Hero(favoriteResults.getId(), favoriteResults.getOriginalTitle(),
//                        favoriteResults.getPosterPath(), favoriteResults.getReleaseDate(), favoriteResults.getVoteAverage(), favoriteResults.getOverview());
//
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        favoriteDatabaseDao.deleteFavorite(deleteFavoriteItems);
//                        Log.d(TAG, deleteFavoriteItems.getOriginalTitle() + " has been deleted from your favorites.");
//                    }
//                });
//
//            }
//
//            public static final String TAG = "Detail Activity";
//        });
//    }

    private void loadHeroes() {

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, URL_GET_DATA,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Hero hero = new Hero(
                                        obj.getString("name"),
                                        obj.getString("realname"),
                                        obj.getString("team"),
                                        obj.getString("firstappearance"),
                                        obj.getString("createdby"),
                                        obj.getString("publisher"),
                                        obj.getString("imageurl"),
                                        obj.getString("bio")
                                );

                                heroList.add(hero);
                            }

                            adapter = new HeroAdapter(getApplicationContext(), heroList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
}

        private void loadFavorites() {


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG, "Preferences updated.");
        checkSortOrder();

    }
    private void checkSortOrder() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_all),
                this.getString(R.string.pref_sort_favorite));

        if (sortOrder.equals(this.getString(R.string.pref_sort_all))) {
            Log.d(LOG, "Sort by all heroes.");
            loadHeroes();

        } else (sortOrder.equals(this.getString(R.string.pref_sort_favorite))); {
            Log.d(LOG, "Sort by favorites.");
            loadFavorites();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (heroList.isEmpty()){
            checkSortOrder();
        } else {

            checkSortOrder();
        }
    }
}

