package com.cicconi.events.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.cicconi.events.CategoryType;
import com.cicconi.events.database.AppDatabase;
import com.cicconi.events.database.Event;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.sql.Timestamp;
import java.util.List;

public class EventRepository {

    private static AppDatabase mDb;

    public EventRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public LiveData<List<Event>> getLocalEvents(CategoryType categoryType, Long date) {
        switch (categoryType) {
            case FAVORITE:
                return mDb.eventDAO().loadFavoriteEvents();
            case DATE:
                return mDb.eventDAO().loadEventsOnDate(date);
            default:
                return mDb.eventDAO().loadAllEvents();
        }
    }

    public Single<Event> getEventByApiId(String apiId) {
        return mDb.eventDAO().loadEventByApiId(apiId)
            .subscribeOn(Schedulers.io());
    }

    public LiveData<Boolean> getEventFavoriteStatus(int id) {
        return mDb.eventDAO().loadEventFavoriteStatus(id);
    }

    public Single<Long> addEvent(Event event) {
        return mDb.eventDAO().insertEvent(event)
            .subscribeOn(Schedulers.io());
    }

    public Completable updateFavoriteEventStatus(int id, boolean isFavorite) {
        return mDb.eventDAO().updateFavoriteEventStatus(id, isFavorite)
            .subscribeOn(Schedulers.io())
            .onErrorComplete();
    }

    public List<Event> getTodayEventsForWidget() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return mDb.eventDAO().loadTodayEventsForWidget(timestamp.getTime());
    }
}
