package activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.like.OnLikeListener;
import com.raemacias.superheroes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.HeroAdapter;
import database.AppExecutors;
import database.FavoriteDatabase;
import database.FavoriteItemDao;
import models.FavoriteViewModel;
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
    private static final String RECYCLER_POSITION = "RecyclerViewPosition";

    AdView mAdView;
    RecyclerView recyclerView;
    HeroAdapter adapter;
    List<Hero> heroList;
    static List<Hero> favoriteHeroes;
    FavoriteDatabase db;
    private FavoriteItemDao favoriteDatabaseDao;

    private Parcelable recyclerPosition;

    private String TAG = "Main Activity";
    private String LOG = "";
    private SharedPreferences mSharedPreferences;
    private FavoriteViewModel mViewModel;
    LikeButton heartButton;
    Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final LikeButton heartButton = findViewById(R.id.heart_button);

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

        initHeroView();
        initFavoriteView();
        loadHeroes();

    }

    //This code came from Stack Overflow:
    //https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_POSITION,
                recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(RECYCLER_POSITION)) {
            recyclerPosition = savedInstanceState.getParcelable(RECYCLER_POSITION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_all:
                loadHeroes();
                break;

            case R.id.menu_favorite:
                recyclerView.setAdapter(new HeroAdapter(getApplicationContext(), favoriteHeroes));
                loadFavorites();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initHeroView() {
        checkSortOrder();
    }

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

        private void initFavoriteView() {
        checkSortOrder();
    }
    //for loading favorites from DB
    private void loadFavorites() {

        mViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        mViewModel.getFavoriteItems().observe(this, new Observer<List<Hero>>() {
            @Override
            public void onChanged(@Nullable final List<Hero> heroList) {
                favoriteHeroes = heroList;
            }
        });

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

        } else {
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

