package com.cicconi.events;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.cicconi.events.adapter.EventAdapter;
import com.cicconi.events.database.Event;
import com.cicconi.events.viewmodel.MainViewModel;
import com.cicconi.events.worker.SyncEventsWorker;
import com.facebook.stetho.Stetho;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements EventAdapter.EventClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private EventAdapter mEventAdapter;
    private GridLayoutManager layoutManager;

    private MainViewModel mViewModel;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;
    private TextView mNoResultsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Used to debug database content
        Stetho.initializeWithDefaults(this);

        synchronizeEvents();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mNoResultsMessage = findViewById(R.id.tv_no_results_message);
        mRecyclerView = findViewById(R.id.recyclerview_recipes);

        final int columns = getResources().getInteger(R.integer.main_recipe_list_columns);
        layoutManager = new GridLayoutManager(this, columns, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mEventAdapter = new EventAdapter(this);
        mRecyclerView.setAdapter(mEventAdapter);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        loadEvents();
    }

    private void synchronizeEvents() {
        Constraints constraints = new Constraints.Builder()
            .setRequiresCharging(true)
            .build();

        PeriodicWorkRequest syncRecipesWorkRequest =
            new PeriodicWorkRequest.Builder(SyncEventsWorker.class, 1, TimeUnit.DAYS)
                //.setConstraints(constraints)
                .build();

        WorkManager
            .getInstance(this)
            .enqueue(syncRecipesWorkRequest);
    }

    private void loadEvents() {
        displayLoading();
        setCategory();
        observeEventsData();
    }

    private void displayLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void setCategory() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_CATEGORY_TYPE)) {
            CategoryType categoryType = (CategoryType) intent.getSerializableExtra(Constants.EXTRA_CATEGORY_TYPE);
            mViewModel.setCategory(categoryType);
        }
    }

    private void observeEventsData() {
        mViewModel.getEvents().observe(this, events -> {
            Log.i(TAG, "events live data changed");
            onEventsReceived(events);
        });
    }

    private void onEventsReceived(List<Event> events) {
        if (!events.isEmpty()) {
            showEventView();
            mEventAdapter.setEventData(events);
        } else {
            showErrorMessage();
        }
    }

    private void showEventView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mNoResultsMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEventItemClick(Event event) {
        /*Intent recipeDetailsActivityIntent = new Intent(this, RecipeDetailsActivity.class);
        recipeDetailsActivityIntent.putExtra(Constants.EXTRA_RECIPE, event);
        startActivity(recipeDetailsActivityIntent);*/
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

        if (id == R.id.action_recipes_all) {
            mViewModel.setCategory(CategoryType.ALL);

            return true;
        }

        if (id == R.id.action_recipes_favorite) {
            mViewModel.setCategory(CategoryType.FAVORITE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
