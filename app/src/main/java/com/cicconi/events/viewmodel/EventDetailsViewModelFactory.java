package com.cicconi.events.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.cicconi.events.database.Event;

public class EventDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Context context;

    private final Event event;

    public EventDetailsViewModelFactory(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EventDetailsViewModel(context, event);
    }
}
