package com.cicconi.events;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.cicconi.events.adapter.EventAdapter;
import com.cicconi.events.database.Event;
import com.cicconi.events.databinding.ActivityMainBinding;
import com.cicconi.events.viewmodel.MainViewModel;
import com.cicconi.events.async.SyncEventsWorker;
import com.facebook.stetho.Stetho;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements EventAdapter.EventClickListener {

    private EventAdapter mEventAdapter;
    private MainViewModel mViewModel;
    private ActivityMainBinding mBinding;
    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Used to debug database content
        Stetho.initializeWithDefaults(this);

        synchronizeEvents();

        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if(findViewById(R.id.event_details_fragment) != null) { isTablet = true; }

        final int columns = getResources().getInteger(R.integer.main_events_list_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(
            this, columns, GridLayoutManager.VERTICAL, false);
        mBinding.recyclerviewEvents.setLayoutManager(layoutManager);
        mBinding.recyclerviewEvents.setHasFixedSize(true);

        mEventAdapter = new EventAdapter(this);
        mBinding.recyclerviewEvents.setAdapter(mEventAdapter);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        loadEvents();
    }

    private void synchronizeEvents() {
        Constraints constraints = new Constraints.Builder()
            .setRequiresCharging(true)
            .build();

        PeriodicWorkRequest syncEventsWorkRequest =
            new PeriodicWorkRequest.Builder(SyncEventsWorker.class, 1, TimeUnit.DAYS)
                //.setConstraints(constraints)
                .build();

        WorkManager
            .getInstance(this)
            .enqueue(syncEventsWorkRequest);
    }

    private void loadEvents() {
        showLoading();
        setType();
        observeEventsData();
    }

    private void setType() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_TYPE)) {
            Type type = (Type) intent.getSerializableExtra(Constants.EXTRA_TYPE);
            mViewModel.setType(type, null, null, null);
        }
    }

    private void observeEventsData() {
        mViewModel.getEvents().observe(this, events -> {
            Timber.i("events live data changed");
            onEventsReceived(events);
        });
    }

    private void onEventsReceived(List<Event> events) {
        if (!events.isEmpty()) {
            showEventView();
            mEventAdapter.setEventData(events);

            if(isTablet) {
                loadEventDetails(events.get(0));
            }
        } else {
            showNoResultsMessage();
        }
    }

    private void showLoading() {
        mBinding.loadingIndicator.setVisibility(View.VISIBLE);
        mBinding.noResultsMessage.setVisibility(View.INVISIBLE);
        mBinding.recyclerviewEvents.setVisibility(View.VISIBLE);
    }

    private void showEventView() {
        mBinding.loadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.noResultsMessage.setVisibility(View.INVISIBLE);
        mBinding.recyclerviewEvents.setVisibility(View.VISIBLE);
    }

    private void showNoResultsMessage() {
        mBinding.loadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.noResultsMessage.setVisibility(View.VISIBLE);
        mBinding.recyclerviewEvents.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEventItemClick(Event event) {
        if(isTablet) {
            loadEventDetails(event);
        } else {
            navigateToEventDetails(event);
        }
    }

    private void loadEventDetails(Event event) {
        EventDetailsFragment eventDetailsFragment = EventDetailsFragment.newInstance(event);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.event_details_fragment, eventDetailsFragment);
        fragmentTransaction.commit();
    }

    private void navigateToEventDetails(Event event) {
        Intent eventDetailsActivityIntent = new Intent(this, EventDetailsActivity.class);
        eventDetailsActivityIntent.putExtra(Constants.EXTRA_EVENT, event);
        startActivity(eventDetailsActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_events_all) {
            mViewModel.setType(Type.ALL, null, null, null);

            return true;
        }

        if (id == R.id.action_events_favorite) {
            mViewModel.setType(Type.FAVORITE, null, null, null);

            return true;
        }

        if (id == R.id.action_events_date) {
            DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");

            return true;
        }

        if (id == R.id.action_events_zip_code) {
            ZipCodeDialogFragment newFragment = new ZipCodeDialogFragment();
            newFragment.show(getSupportFragmentManager(), "zipCode");

            return true;
        }

        if (id == R.id.action_events_category) {
            CategoryDialogFragment newFragment = new CategoryDialogFragment();
            newFragment.show(getSupportFragmentManager(), "category");

            return true;
        }

        if (id == R.id.action_events_map) {
            Intent mapsActivityIntent = new Intent(this, MapsActivity.class);
            startActivity(mapsActivityIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDateSet(Long timestamp) {
        mViewModel.setType(Type.DATE, timestamp, null, null);
    }

    public void onZipCodesSet(ArrayList<String> selectedItems) {
        mViewModel.setType(Type.ZIP_CODE, null, selectedItems, null);
    }

    public void onCategorySet(String category) {
        mViewModel.setType(Type.CATEGORY, null, null, category);
    }
}
