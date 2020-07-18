package com.cicconi.events.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.cicconi.events.database.Event;
import com.cicconi.events.repository.EventRepository;
import io.reactivex.Completable;

public class EventDetailsViewModel extends ViewModel {

    private Event mEvent;

    private EventRepository eventRepository;
    private LiveData<Boolean> isFavoriteEvent;

    EventDetailsViewModel(@NonNull Context context, Event event) {
        eventRepository = new EventRepository(context);

        this.mEvent = event;
        isFavoriteEvent = eventRepository.getEventFavoriteStatus(mEvent.getApiId());
    }

    public LiveData<Boolean> getEventFavoriteStatus() {
        return isFavoriteEvent;
    }

    public Completable onFavoriteStatusUpdated(Boolean newFavoriteStatus) {
        return eventRepository.updateFavoriteEventStatus(mEvent.getApiId(), newFavoriteStatus);
    }
}
