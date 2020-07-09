package com.cicconi.events;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.cicconi.events.database.Event;
import com.cicconi.events.database.EventPriceType;
import com.cicconi.events.databinding.ActivityEventDetailsBinding;
import com.cicconi.events.utils.ColorHandler;
import com.cicconi.events.utils.DateFormatter;
import com.cicconi.events.viewmodel.EventDetailsViewModel;
import com.cicconi.events.viewmodel.EventDetailsViewModelFactory;
import com.squareup.picasso.Picasso;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class EventDetailsActivity extends AppCompatActivity {

    private boolean isTablet = false;

    private Event mEvent;
    private ActivityEventDetailsBinding mBinding;
    private EventDetailsViewModel mViewModel;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_details);

        compositeDisposable = new CompositeDisposable();

        if(findViewById(R.id.fragment_step_details_container) != null) {
            isTablet = true;
        }

        if (getIntent().getExtras() != null && getIntent().hasExtra(Constants.EXTRA_EVENT)) {
            mEvent = (Event) getIntent().getExtras().getSerializable(Constants.EXTRA_EVENT);

            if(null == mEvent) {
                showErrorMessage();
            } else {
                EventDetailsViewModelFactory factory = new EventDetailsViewModelFactory(this, mEvent);
                mViewModel = new ViewModelProvider(this, factory).get(EventDetailsViewModel.class);

                showEventView();
            }
        }
    }

    private void showErrorMessage() {
        mBinding.eventDetailsLayout.setVisibility(View.GONE);
        mBinding.errorMessage.setVisibility(View.VISIBLE);
    }

    private void showEventView() {
        mBinding.eventDetailsLayout.setVisibility(View.VISIBLE);
        mBinding.errorMessage.setVisibility(View.GONE);
        handleFavoriteIcon();

        Picasso.with(this)
            .load(mEvent.getCoverUrl())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(mBinding.eventCover);

        if(mEvent.getTitle() != null && !mEvent.getTitle().isEmpty()) {
            mBinding.eventTitle.setText(mEvent.getTitle());
        }

        mBinding.eventDate.setText(DateFormatter.format(this, mEvent.getDateStart()));

        if(mEvent.getEventPriceType() == EventPriceType.FREE) {
            mBinding.eventPrice.setText(R.string.free);
        } else if (mEvent.getPriceDetail()!= null && !mEvent.getPriceDetail().isEmpty()){
            mBinding.eventPrice.setText(mEvent.getPriceDetail());
        } else {
            mBinding.eventPrice.setText(R.string.unknown);
        }

        if(mEvent.getDescription()!= null && !mEvent.getDescription().isEmpty()) {
            mBinding.eventDescriptionLabel.setVisibility(View.VISIBLE);
            mBinding.eventDescription.setVisibility(View.VISIBLE);
            mBinding.eventDescription.setText(mEvent.getDescription());
        }

        if(mEvent.getAddressStreet() != null && !mEvent.getAddressStreet().isEmpty()
            && mEvent.getAddressZipcode() != null && !mEvent.getAddressZipcode().isEmpty()) {
            mBinding.eventAddressLabel.setVisibility(View.VISIBLE);
            mBinding.eventAddress.setVisibility(View.VISIBLE);
            mBinding.eventAddress.setText(
                String.format("%s - %s", mEvent.getAddressStreet(), mEvent.getAddressZipcode()));
        }

        if(mEvent.getTransport() != null && !mEvent.getTransport().isEmpty()) {
            mBinding.eventTransportationLabel.setVisibility(View.VISIBLE);
            mBinding.eventTransportation.setVisibility(View.VISIBLE);
            mBinding.eventTransportation.setText(mEvent.getTransport());
        }

        if((mEvent.getContactUrl() != null && !mEvent.getContactUrl().isEmpty()) ||
            (mEvent.getContactFacebook() != null && !mEvent.getContactFacebook().isEmpty()) ||
            (mEvent.getContactMail() != null && !mEvent.getContactMail().isEmpty()) ||
            (mEvent.getContactPhone() != null && !mEvent.getContactPhone().isEmpty()) ||
            (mEvent.getContactName() != null && !mEvent.getContactName().isEmpty())) {
            mBinding.eventContactLabel.setVisibility(View.VISIBLE);
        }

        if(mEvent.getContactUrl() != null && !mEvent.getContactUrl().isEmpty()) {
            mBinding.eventContactUrl.setVisibility(View.VISIBLE);
            mBinding.eventContactUrl.setText(mEvent.getContactUrl());
        }

        if(mEvent.getContactMail() != null && !mEvent.getContactMail().isEmpty()) {
            mBinding.eventContactEmail.setVisibility(View.VISIBLE);
            mBinding.eventContactEmail.setText(mEvent.getContactMail());
        }

        if(mEvent.getContactFacebook() != null && !mEvent.getContactFacebook().isEmpty()) {
            mBinding.eventContactFacebook.setVisibility(View.VISIBLE);
            mBinding.eventContactFacebook.setText(mEvent.getContactFacebook());
        }

        if(mEvent.getContactPhone() != null && !mEvent.getContactPhone().isEmpty()) {
            mBinding.eventContactPhone.setVisibility(View.VISIBLE);
            mBinding.eventContactPhone.setText(mEvent.getContactPhone());
        }
    }

    private void handleFavoriteIcon() {
        mViewModel.getEventFavoriteStatus().observe(this, isFavorite -> {
            Timber.i("isFavorite live data changed: %s", isFavorite);
            setFavoriteIconColor(isFavorite);
            onFavoriteIconClick(isFavorite);
        });
    }

    private void onFavoriteIconClick(boolean isFavorite) {
        mBinding.eventFavorite.setOnClickListener(view -> {
            updateRecipeFavoriteStatus(isFavorite);
        });
    }

    private void updateRecipeFavoriteStatus(boolean isFavorite) {
        boolean newFavoriteStatus = !isFavorite;
        Disposable disposable = mViewModel.onFavoriteStatusUpdated(newFavoriteStatus)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(e -> {
                e.printStackTrace();
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
            })
            .subscribe(
                () -> {
                    setFavoriteIconColor(newFavoriteStatus);
                    displayFavoriteUpdateStatusMessage(newFavoriteStatus);
                },
                Throwable::printStackTrace
            );

        compositeDisposable.add(disposable);
    }

    private void setFavoriteIconColor(Boolean isFavorite) {
        if(isFavorite) {
            mBinding.eventFavorite.setColorFilter(ColorHandler.getFavoriteIconColor(this));
        } else {
            mBinding.eventFavorite.setColorFilter(ColorHandler.getNonFavoriteIconColor(this));
        }
    }

    private void displayFavoriteUpdateStatusMessage(Boolean isFavorite) {
        if(isFavorite) {
            Toast.makeText(this, "The event was added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The event was removed from favorites", Toast.LENGTH_SHORT).show();
        }
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
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);

            return true;
        }

        if (id == R.id.action_recipes_favorite) {
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.putExtra(Constants.EXTRA_CATEGORY_TYPE, CategoryType.FAVORITE);
            startActivity(mainActivityIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isTablet() {
        return isTablet;
    }
}
