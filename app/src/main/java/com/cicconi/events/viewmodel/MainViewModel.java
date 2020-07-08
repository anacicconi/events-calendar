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

    private static final String TAG = MainViewModel.class.getSimpleName();

    private final MutableLiveData<CategoryType> category = new MutableLiveData();
    private LiveData<List<Event>> events;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        EventRepository eventRepository = new EventRepository(context);

        //recipes = recipeRepository.getLocalRecipes();
        // The recipes will be returned based on the value set for category
        category.setValue(CategoryType.ALL);
        events = Transformations.switchMap(category, (value) -> eventRepository.getLocalEvents(value));
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void setCategory(CategoryType categoryType) {
        category.setValue(categoryType);
    }
}
