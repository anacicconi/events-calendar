package com.cicconi.events.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM event ORDER BY date_start ASC")
    LiveData<List<Event>> loadAllEvents();

    @Query("SELECT * FROM event WHERE favorite = 1 ORDER BY date_start ASC")
    LiveData<List<Event>> loadFavoriteEvents();

    @Query("SELECT * FROM event WHERE date_start < :date and date_end > :date ORDER BY date_start ASC")
    LiveData<List<Event>> loadEventsOnDate(Long date);

    @Query("SELECT * FROM event WHERE address_zipcode IN (:zipCodes) ORDER BY date_start ASC")
    LiveData<List<Event>> loadEventsOnZipCodes(ArrayList<String> zipCodes);

    @Query("SELECT * FROM event WHERE category LIKE '%' || :category || '%' ORDER BY date_start ASC")
    LiveData<List<Event>> loadEventsOnCategory(String category);

    @Query("SELECT * FROM event WHERE date_start < :date and date_end > :date ORDER BY date_start ASC")
    List<Event> loadTodayEventsForWidget(long date);

    // Not used on a view so no need for a live data
    @Query("SELECT * FROM event WHERE apiId = :apiId")
    Single<Event> loadEventByApiId(String apiId);

    @Query("SELECT favorite FROM event WHERE apiId = :apiId")
    LiveData<Boolean> loadEventFavoriteStatus(String apiId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertEvent(Event event);

    @Query("DELETE FROM event WHERE date_end < strftime('%s','now')")
    Single<Integer> deletePastEvents();

    @Query("DELETE FROM event WHERE apiId = :apiId")
    Completable deleteEvent(String apiId);

    @Query("UPDATE event SET favorite = :isFavorite WHERE apiId = :apiId")
    Completable updateFavoriteEventStatus(String apiId, boolean isFavorite);
}
