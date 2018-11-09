//package activities;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.RequiresApi;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.like.LikeButton;
//import com.like.OnAnimationEndListener;
//import com.like.OnLikeListener;
//import com.raemacias.superheroes.R;
//
//import activities.MainActivity;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class ButtonActivity extends MainActivity implements OnLikeListener,
//        OnAnimationEndListener {
//
//    public static final String TAG = "MainActivity";
//    public static final String FAVORITE_ID = "favorite";
//
//    @BindView(R.id.heart_button)
//    LikeButton likeButton;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_layout_heroes);
//        ButterKnife.bind(this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//
//        likeButton.setOnAnimationEndListener(this);
//        likeButton.setOnLikeListener(this);
//
//        likeButton.setOnLikeListener(this);
//        likeButton.setOnAnimationEndListener(this);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void liked(LikeButton likeButton) {
//        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void unLiked(LikeButton likeButton) {
//        Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override public void onAnimationEnd(LikeButton likeButton) {
//        Log.d(TAG, "Animation End for %s" + likeButton);
//    }
//
//    @OnClick(R.id.heart_button)
//    public void navigateToList()
//    {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//    }
//}