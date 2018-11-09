//package activities;
//
//import android.annotation.TargetApi;
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.like.LikeButton;
//import com.like.OnLikeListener;
//import com.raemacias.superheroes.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//import database.AppExecutors;
//import database.FavoriteDatabase;
//import database.FavoriteItemDao;
//import models.FavoriteViewModel;
//import models.Hero;
//
//public class DetailActivity extends AppCompatActivity {
//
//    private static final String TAG = "Detail Activity" ;
//    FavoriteDatabase db;
//    private FavoriteItemDao favoriteDatabaseDao;
//    static Hero favoriteHeroes;
//
//    private final AppCompatActivity activity = DetailActivity.this;
//    FavoriteViewModel mViewModel;
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//
//    Boolean isFavorite = false;
//
//    TextView textViewName, textViewRealName, textViewTeam, textViewFirstAppearance,
//            textViewCreatedBy, textViewPublisher, textViewBio;
//    ImageView imageView;
//    LinearLayout linearLayout;
//    LikeButton heartButton;
//
//
//
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_layout_heroes);
//
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        getSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        db = FavoriteDatabase.getFavoriteDatabase(this);
//        favoriteDatabaseDao = db.mFavoriteItemDao();
//
//        textViewName = findViewById(R.id.textViewName);
//        textViewRealName = findViewById(R.id.textViewRealName);
//        textViewTeam = findViewById(R.id.textViewTeam);
//        textViewFirstAppearance = findViewById(R.id.textViewFirstAppearance);
//        textViewCreatedBy = findViewById(R.id.textViewCreatedBy);
//        textViewPublisher = findViewById(R.id.textViewPublisher);
//        textViewBio = findViewById(R.id.textViewBio);
//        imageView = findViewById(R.id.imageView);
//
//        linearLayout = findViewById(R.id.linearLayout);
//
//        final LikeButton heartButton = findViewById(R.id.heart_button);
//
//        Intent intent = getIntent();
//        if (intent.hasExtra("name")) {
//            String name = getIntent().getExtras().getString("name");
//            String realname = getIntent().getExtras().getString("realname");
//            String team = getIntent().getExtras().getString("team");
//            String firstappearance = getIntent().getExtras().getString("firstappearance");
//            String createdby = getIntent().getExtras().getString("createdby");
//            String publisher = getIntent().getExtras().getString("publisher");
//            String imageurl = getIntent().getExtras().getString("imageurl");
//            String bio = getIntent().getExtras().getString("bio");
//            assert name != null;
//            favoriteHeroes = new Hero(name, realname, team, firstappearance, createdby, publisher, imageurl, bio);
//
//            mViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
//
//            // Add an observer on the LiveData returned by getAlphabetizedWords.
//            // The onChanged() method fires when the observed data changes and the activity is
//            // in the foreground.
//            mViewModel.getFavoriteItems().observe(this, new Observer<List<Hero>>() {
//                @Override
//                public void onChanged(@Nullable final List<Hero> favoriteEntries) {
//                    for (Hero item : favoriteEntries) {
//                        if (item.getName() == favoriteHeroes.getName()) {
//                            heartButton.setLiked(true);
//                        }
//                    }
//                    // Update the cached copy of the words in the adapter.
//                }
//            });
//
//            Picasso.get()
//                    .load("imageurl")
//                    .placeholder(R.drawable.popcorn)
//                    .into(imageView);
//
//            textViewName.setText(name);
//            textViewRealName.setText(realname);
//            textViewTeam.setText(team);
//            textViewFirstAppearance.setText(firstappearance);
//            textViewCreatedBy.setText(createdby);
//            textViewPublisher.setText(publisher);
//            textViewBio.setText(bio);
//
//        } else {
//            Toast.makeText(this, "Information not available.", Toast.LENGTH_SHORT).show();
//        }
//
////
////        loadHeroes();
////        loadFavorites();
//
//        heartButton.setOnLikeListener(new OnLikeListener() {
//
//            public void liked(LikeButton likeButton) {
//                // Code here executes on main thread after user presses button
//                final Hero getFavoriteItems = new Hero(favoriteHeroes.getName(), favoriteHeroes.getRealname(),
//                        favoriteHeroes.getTeam(), favoriteHeroes.getFirstappearance(), favoriteHeroes.getCreatedby(), favoriteHeroes.getPublisher(), favoriteHeroes.getImageurl(), favoriteHeroes.getBio());
//
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        db.mFavoriteItemDao().insertFavorite(getFavoriteItems);
//                    }
//                });
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//            final Hero deleteFavoriteItems = new Hero(favoriteHeroes.getName(), favoriteHeroes.getRealname(),
//                    favoriteHeroes.getTeam(), favoriteHeroes.getFirstappearance(), favoriteHeroes.getCreatedby(), favoriteHeroes.getPublisher(), favoriteHeroes.getImageurl(), favoriteHeroes.getBio());
//
//            AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                @Override
//                public void run() {
//                    favoriteDatabaseDao.deleteFavorite(deleteFavoriteItems);
//                    Log.d(TAG, deleteFavoriteItems.getName() + " has been deleted from your favorites.");
//                }
//            });
//
//            }
//        });
//    }
//}
//
