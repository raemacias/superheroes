package models;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import database.FavoriteDatabase;

public class FavoriteViewModelFactory extends ViewModelProvider.NewInstanceFactory{

//    private final FavoriteDatabase db;
    private final Application mApplication;
    private final FavoriteDatabase mFavorites;

    public FavoriteViewModelFactory(Application application, FavoriteDatabase favorites) {
        mApplication = application;
        mFavorites = favorites;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new FavoriteViewModel(mApplication);
    }
}
