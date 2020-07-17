package com.cicconi.events;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.cicconi.events.database.Event;
import com.cicconi.events.database.EventPriceType;
import com.cicconi.events.databinding.FragmentEventDetailsBinding;
import com.cicconi.events.utils.ColorHandler;
import com.cicconi.events.utils.DateFormatter;
import com.cicconi.events.utils.PhoneFomatter;
import com.cicconi.events.viewmodel.EventDetailsViewModel;
import com.cicconi.events.viewmodel.EventDetailsViewModelFactory;
import com.squareup.picasso.Picasso;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

public class EventDetailsFragment extends Fragment {

    private Event mEvent;
    private FragmentEventDetailsBinding mBinding;
    private EventDetailsViewModel mViewModel;
    private CompositeDisposable compositeDisposable;

    public EventDetailsFragment() {}

    static EventDetailsFragment newInstance(Event event) {
        EventDetailsFragment fragment = new EventDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_EVENT, event);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false);

        Intent intent = requireActivity().getIntent();

        // check if the fragment received something through intent
        getIntentData(intent);

        if(null == mEvent) {
            // check if the fragment received something during the creation
            getArgumentsData();
        }

        if(null == mEvent) {
            showErrorMessage();
        } else {
            showEventView();
        }

        return mBinding.getRoot();
    }

    private void getIntentData(Intent intent) {
        if (intent.getExtras() != null && intent.hasExtra(Constants.EXTRA_EVENT)) {
            mEvent = (Event) intent.getExtras().getSerializable(Constants.EXTRA_EVENT);
        }
    }

    private void getArgumentsData() {
        if (getArguments() != null) {
            mEvent = (Event) getArguments().getSerializable(Constants.EXTRA_EVENT);
        }
    }

    private void showErrorMessage() {
        mBinding.eventDetailsLayout.setVisibility(View.GONE);
        mBinding.errorMessage.setVisibility(View.VISIBLE);
    }

    private void showEventView() {
        EventDetailsViewModelFactory factory = new EventDetailsViewModelFactory(requireContext(), mEvent);
        mViewModel = new ViewModelProvider(this, factory).get(EventDetailsViewModel.class);

        mBinding.eventDetailsLayout.setVisibility(View.VISIBLE);
        mBinding.errorMessage.setVisibility(View.GONE);
        handleFavoriteIconClick();
        handleShareIconClick();
        handleUrlClick();
        handleFacebookUrlClick();
        handlePhoneClick();
        handleEmailClick();

        Picasso.with(requireActivity())
            .load(mEvent.getCoverUrl())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(mBinding.eventCover);

        if(mEvent.getTitle() != null && !mEvent.getTitle().isEmpty()) {
            mBinding.eventTitle.setText(mEvent.getTitle());
        }

        String finalDate;
        try {
            finalDate = DateFormatter.format(mEvent.getDateStart(), mEvent.getDateEnd());
        } catch(Exception ex) {
            Timber.i("Not able to parse event dates: %s - %s", mEvent.getDateStart(), mEvent.getDateEnd());
            finalDate = getString(R.string.unknown);
        }

        mBinding.eventDate.setText(finalDate);

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
            String description = Html.fromHtml(mEvent.getDescription()).toString();
            mBinding.eventDescription.setText(description);
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

        if(mEvent.getContactName() != null && !mEvent.getContactName().isEmpty()) {
            mBinding.eventContactName.setVisibility(View.VISIBLE);
            mBinding.eventContactName.setText(mEvent.getContactName());
            mBinding.eventContactName.setPaintFlags(mBinding.eventContactName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        if(mEvent.getContactUrl() != null && !mEvent.getContactUrl().isEmpty()) {
            mBinding.eventContactUrl.setVisibility(View.VISIBLE);
            mBinding.eventContactUrl.setText(mEvent.getContactUrl());
            mBinding.eventContactUrl.setPaintFlags(mBinding.eventContactUrl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        if(mEvent.getContactMail() != null && !mEvent.getContactMail().isEmpty()) {
            mBinding.eventContactEmail.setVisibility(View.VISIBLE);
            mBinding.eventContactEmail.setText(mEvent.getContactMail());
            mBinding.eventContactEmail.setPaintFlags(mBinding.eventContactEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        if(mEvent.getContactFacebook() != null && !mEvent.getContactFacebook().isEmpty()) {
            mBinding.eventContactFacebook.setVisibility(View.VISIBLE);
            mBinding.eventContactFacebook.setText(mEvent.getContactFacebook());
            mBinding.eventContactFacebook.setPaintFlags(mBinding.eventContactFacebook.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        if(mEvent.getContactPhone() != null && !mEvent.getContactPhone().isEmpty()) {
            mBinding.eventContactPhone.setVisibility(View.VISIBLE);
            mBinding.eventContactPhone.setText(PhoneFomatter.format(mEvent.getContactPhone()));
        }
    }

    private void handleFavoriteIconClick() {
        mViewModel.getEventFavoriteStatus().observe(requireActivity(), isFavorite -> {
            Timber.i("isFavorite live data changed: %s", isFavorite);
            setFavoriteIconColor(isFavorite);
            onFavoriteIconClick(isFavorite);
        });
    }

    private void handleShareIconClick() {
        mBinding.eventShare.setOnClickListener(view -> {
            String mimeType = "text/plain";
            String textToShare =
                String.format("Je pense que cet événement va vous intérésser: %s. Pour en savoir plus: %s",
                    mEvent.getTitle(), mEvent.getContactUrl());

            ShareCompat.IntentBuilder shareCompat = ShareCompat.IntentBuilder.from(requireActivity());
            shareCompat
                .setType(mimeType)
                .setText(textToShare)
                .startChooser();
        });
    }

    private void handleUrlClick() {
        mBinding.eventContactUrl.setOnClickListener(view -> {
            Uri webPage = Uri.parse(mBinding.eventContactUrl.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

            if(intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }

    private void handleFacebookUrlClick() {
        mBinding.eventContactFacebook.setOnClickListener(view -> {
            Uri webPage = Uri.parse(mBinding.eventContactFacebook.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

            if(intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }

    private void handlePhoneClick() {
        mBinding.eventContactPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mBinding.eventContactPhone.getText()));
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }

    private void handleEmailClick() {
        mBinding.eventContactEmail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, mBinding.eventContactEmail.getText());
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }

    private void onFavoriteIconClick(boolean isFavorite) {
        mBinding.eventFavorite.setOnClickListener(view -> {
            updateEventFavoriteStatus(isFavorite);
        });
    }

    private void updateEventFavoriteStatus(boolean isFavorite) {
        boolean newFavoriteStatus = !isFavorite;
        Disposable disposable = mViewModel.onFavoriteStatusUpdated(newFavoriteStatus)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(e -> {
                e.printStackTrace();
                Toast.makeText(requireActivity(),  R.string.error_message, Toast.LENGTH_SHORT).show();
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
            mBinding.eventFavorite.setColorFilter(ColorHandler.getFavoriteIconColor(mBinding.eventFavorite.getContext()));
        } else {
            mBinding.eventFavorite.setColorFilter(ColorHandler.getNonFavoriteIconColor(mBinding.eventFavorite.getContext()));
        }
    }

    private void displayFavoriteUpdateStatusMessage(Boolean isFavorite) {
        if(isFavorite) {
            Toast.makeText(requireActivity(), R.string.event_added_favorite, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), R.string.event_removed_favorite, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
