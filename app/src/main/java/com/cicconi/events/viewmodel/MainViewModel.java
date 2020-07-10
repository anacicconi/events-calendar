package com.cicconi.events.viewmodel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.cicconi.events.CategoryType;
import com.cicconi.events.database.Event;
import com.cicconi.events.repository.EventRepository;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<CategoryType> category = new MutableLiveData();
    private LiveData<List<Event>> events;
    private Long date;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        EventRepository eventRepository = new EventRepository(context);

        // The events will be returned based on the value set for category and the date
        category.setValue(CategoryType.ALL);
        events = Transformations.switchMap(category, categoryType -> eventRepository.getLocalEvents(categoryType, date));
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void setCategory(CategoryType categoryType, Long dateFilter) {
        if(dateFilter != null) {
            date = dateFilter;
        }
        category.setValue(categoryType);
    }
}
