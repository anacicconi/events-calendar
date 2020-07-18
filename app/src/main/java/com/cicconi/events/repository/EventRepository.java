package com.cicconi.events.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.cicconi.events.Type;
import com.cicconi.events.database.AppDatabase;
import com.cicconi.events.database.Event;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private static AppDatabase mDb;

    public EventRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public LiveData<List<Event>> getLocalEvents(Type type, Long date, ArrayList<String> zipCodes, String category) {
        switch (type) {
            case FAVORITE:
                return mDb.eventDAO().loadFavoriteEvents();
            case DATE:
                return mDb.eventDAO().loadEventsOnDate(date);
            case ZIP_CODE:
                return mDb.eventDAO().loadEventsOnZipCodes(zipCodes);
            case CATEGORY:
                return mDb.eventDAO().loadEventsOnCategory(category);
            default:
                return mDb.eventDAO().loadAllEvents();
        }
    }

    public Single<Event> getEventByApiId(String apiId) {
        return mDb.eventDAO().loadEventByApiId(apiId)
            .subscribeOn(Schedulers.io());
    }

    public LiveData<Boolean> getEventFavoriteStatus(String apiId) {
        return mDb.eventDAO().loadEventFavoriteStatus(apiId);
    }

    public Single<Long> addEvent(Event event) {
        return mDb.eventDAO().insertEvent(event)
            .subscribeOn(Schedulers.io());
    }

    public Completable updateFavoriteEventStatus(String apiId, boolean isFavorite) {
        return mDb.eventDAO().updateFavoriteEventStatus(apiId, isFavorite)
            .subscribeOn(Schedulers.io())
            .onErrorComplete();
    }

    public Single<Integer> deletePastEvents() {
        return mDb.eventDAO().deletePastEvents()
            .subscribeOn(Schedulers.io());
    }

    public List<Event> getTodayEventsForWidget() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return mDb.eventDAO().loadTodayEventsForWidget(timestamp.getTime());
    }
}
