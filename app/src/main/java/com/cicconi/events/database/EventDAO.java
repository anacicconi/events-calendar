package com.cicconi.events.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM event ORDER BY id")
    LiveData<List<Event>> loadAllEvents();

    @Query("SELECT * FROM event WHERE favorite = 1 ORDER BY id")
    LiveData<List<Event>> loadFavoriteEvents();

    @Query("SELECT * FROM event WHERE favorite = 1 ORDER BY id")
    List<Event> loadFavoriteEventsForWidget();

    // Not used on a view so no need for a live data
    @Query("SELECT * FROM event WHERE api_id = :apiId")
    Single<Event> loadEventByApiId(String apiId);

    @Query("SELECT favorite FROM event WHERE id = :id")
    LiveData<Boolean> loadEventFavoriteStatus(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertEvent(Event event);

    @Query("DELETE FROM event WHERE id = :id")
    Completable deleteEvent(int id);

    @Query("UPDATE event SET favorite = :isFavorite WHERE id = :id")
    Completable updateFavoriteEventStatus(int id, boolean isFavorite);
}
