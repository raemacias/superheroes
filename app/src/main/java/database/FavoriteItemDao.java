package database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import models.Hero;

@Dao
public interface FavoriteItemDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<Hero>> loadAllFavorites();

    @Query("SELECT * FROM favorites where name= :name")
    LiveData<Hero> loadFavoriteHeroes (String name);

    @Insert
    void insertFavorite(Hero favoriteHeroes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Hero favoriteHeroes);

    @Delete
    void deleteFavorite(Hero favoriteHeroes);
}

