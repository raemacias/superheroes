package models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import database.FavoriteItemRepository;

public class FavoriteViewModel extends AndroidViewModel {

    private static final String TAG = FavoriteViewModel.class.getSimpleName();
    private FavoriteItemRepository mRepository;

    private LiveData<List<Hero>> favoriteHeroes;

    public FavoriteViewModel(Application application) {
        super(application);
        mRepository = new FavoriteItemRepository(application);
        favoriteHeroes = mRepository.getFavoriteItems();
    }


    public LiveData<List<Hero>> getFavoriteItems() {
        return favoriteHeroes;
    }

    public void insert(Hero favoriteHeroes) {
        mRepository.insert(favoriteHeroes);
    }
}
