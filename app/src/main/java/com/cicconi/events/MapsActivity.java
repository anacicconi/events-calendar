package com.cicconi.events;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.cicconi.events.database.Event;
import com.cicconi.events.database.EventPriceType;
import com.cicconi.events.databinding.ActivityMapsBinding;
import com.cicconi.events.utils.DateFormatter;
import com.cicconi.events.viewmodel.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.List;
import timber.log.Timber;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private MainViewModel mViewModel;
    public static final int DEFAULT_MAPS_ZOOM = 10;
    private ActivityMapsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        observeEventsData();
    }

    private void observeEventsData() {
        mViewModel.getEvents().observe(this, events -> {
            Timber.i("events live data changed");
            onEventsReceived(events);
        });
    }

    private void onEventsReceived(List<Event> events) {
        MarkerOptions options = new MarkerOptions();
        for (Event event: events) {
            LatLng latlng = new LatLng(event.getLatitude(), event.getLongitude());
            options.position(latlng);
            Marker marker = mMap.addMarker(options);
            marker.setTag(event);
        }

        // Add a marker in Paris Eiffel Tower and move the camera
        LatLng eiffelTower = new LatLng(48.8584, 2.2945);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffelTower, DEFAULT_MAPS_ZOOM));

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBinding.mapBottomSheet);

        Event event = (Event) marker.getTag();

        if (event != null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            mBinding.eventTitle.setText(event.getTitle());
            mBinding.eventDate.setText(DateFormatter.format(event.getDateStart(), event.getDateEnd()));

            if(event.getEventPriceType() == EventPriceType.FREE) {
                mBinding.eventPrice.setText(R.string.free);
            } else {
                mBinding.eventPrice.setText(event.getPriceDetail());
            }

            mBinding.btnMore.setOnClickListener(view -> {
                Intent eventDetailsActivityIntent = new Intent(this, EventDetailsActivity.class);
                eventDetailsActivityIntent.putExtra(Constants.EXTRA_EVENT, event);
                startActivity(eventDetailsActivityIntent);
            });
        }

        return false;
    }
}
