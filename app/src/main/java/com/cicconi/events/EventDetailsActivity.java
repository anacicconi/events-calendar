package com.cicconi.events;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import com.cicconi.events.database.Event;
import com.cicconi.events.database.EventPriceType;
import com.cicconi.events.databinding.ActivityEventDetailsBinding;
import com.squareup.picasso.Picasso;

public class EventDetailsActivity extends AppCompatActivity {

    private boolean isTablet = false;

    ActivityEventDetailsBinding mBinding;

    Event mEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_details);

        if(findViewById(R.id.fragment_step_details_container) != null) {
            isTablet = true;
        }

        if (getIntent().getExtras() != null && getIntent().hasExtra(Constants.EXTRA_EVENT)) {
            mEvent = (Event) getIntent().getExtras().getSerializable(Constants.EXTRA_EVENT);

            if(null == mEvent) {
                showErrorMessage();
            } else {
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

        Picasso.with(this)
            .load(mEvent.getCoverUrl())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(mBinding.eventCover);

        if(mEvent.getTitle() != null && !mEvent.getTitle().isEmpty()) {
            mBinding.eventTitle.setText(mEvent.getTitle());
        }

        mBinding.eventDate.setText(mEvent.getDateStart().toString());

        if(mEvent.getEventPriceType() == EventPriceType.FREE) {
            mBinding.eventPrice.setText(R.string.free);
        } else if (mEvent.getPriceDetail()!= null && !mEvent.getPriceDetail().isEmpty()){
            mBinding.eventPrice.setText(mEvent.getPriceDetail());
        } else {
            mBinding.eventPrice.setText(R.string.unknown);
        }

        if(mEvent.getDescription()!= null && !mEvent.getDescription().isEmpty()) {
            mBinding.eventDescriptionLabel.setVisibility(View.VISIBLE);
            mBinding.eventDescription.setText(mEvent.getDescription());
        }

        if(mEvent.getAddressStreet() != null && !mEvent.getAddressStreet().isEmpty()
            && mEvent.getAddressZipcode() != null && !mEvent.getAddressZipcode().isEmpty()) {
            mBinding.eventAddressLabel.setVisibility(View.VISIBLE);
            mBinding.eventAddress.setText(
                String.format("%s - %s", mEvent.getAddressStreet(), mEvent.getAddressZipcode()));
        }

        if(mEvent.getTransport() != null && !mEvent.getTransport().isEmpty()) {
            mBinding.eventTransportationLabel.setVisibility(View.VISIBLE);
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
            mBinding.eventContactUrl.setText(mEvent.getContactUrl());
        }

        if(mEvent.getContactMail() != null && !mEvent.getContactMail().isEmpty()) {
            mBinding.eventContactEmail.setText(mEvent.getContactMail());
        }

        if(mEvent.getContactFacebook() != null && !mEvent.getContactFacebook().isEmpty()) {
            mBinding.eventContactFacebook.setText(mEvent.getContactFacebook());
        }

        if(mEvent.getContactPhone() != null && !mEvent.getContactPhone().isEmpty()) {
            mBinding.eventContactPhone.setText(mEvent.getContactPhone());
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
