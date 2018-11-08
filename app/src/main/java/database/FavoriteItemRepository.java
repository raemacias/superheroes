package database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import models.Hero;

//The use of this repository adds another layer of abstraction
//so the view model doesn't know what kind of database it's talking to.
//"Data hiding in practice."

public class FavoriteItemRepository {

    private final FavoriteItemDao favoriteItemDao;
    private LiveData<List<Hero>> favoriteResults;

    public FavoriteItemRepository(Application application) {
        FavoriteDatabase db = FavoriteDatabase.getFavoriteDatabase(application);
        favoriteItemDao = db.mFavoriteItemDao();
        favoriteResults = favoriteItemDao.loadAllFavorites();
    }

    public LiveData<List<Hero>> getFavoriteItems() {

        return favoriteResults;
    }


    public void insert (Hero favoriteResults) {
        new insertAsyncTask(favoriteItemDao).execute(favoriteResults);
    }

    private static class insertAsyncTask extends AsyncTask<Hero, Void, Void> {

        private FavoriteItemDao mAsyncTaskDao;

        insertAsyncTask(FavoriteItemDao dao) {

            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Hero... params) {
            mAsyncTaskDao.insertFavorite(params[0]);
            return null;
        }

    }
}
