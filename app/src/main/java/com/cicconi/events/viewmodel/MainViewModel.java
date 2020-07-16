package com.cicconi.events.viewmodel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.cicconi.events.Type;
import com.cicconi.events.database.Event;
import com.cicconi.events.repository.EventRepository;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<Type> type = new MutableLiveData();
    private LiveData<List<Event>> events;
    private Long date;
    private ArrayList<String> zipCodes;
    private String category;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        EventRepository eventRepository = new EventRepository(context);

        // The events will be returned based on the value set for the filters
        type.setValue(Type.ALL);
        events = Transformations.switchMap(type, eventType -> eventRepository.getLocalEvents(eventType, date, zipCodes, category));
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void setType(Type setType, Long dateFilter, ArrayList<String> zipCodesFilter, String categoryFilter) {
        if(dateFilter != null) {
            date = dateFilter;
        }
        if(zipCodesFilter != null) {
            zipCodes = zipCodesFilter;
        }
        if(categoryFilter != null) {
            category = categoryFilter;
        }
        type.setValue(setType);
    }
}
