package com.cicconi.events.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cicconi.events.R;
import com.cicconi.events.database.Event;
import com.cicconi.events.utils.DateFormatter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder> {

    private EventClickListener mClickListener;

    private List<Event> mEventData  = new ArrayList<>();

    private Context mContext;

    public EventAdapter(EventClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setEventData(List<Event> eventData) {
        mEventData = eventData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_event, parent, false);

        return new EventAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapterViewHolder holder, int position) {
        String title = mEventData.get(position).getTitle();
        Long startDate = mEventData.get(position).getDateStart();
        Long endDate = mEventData.get(position).getDateEnd();
        String priceType = mEventData.get(position).getPriceType();
        String zipCode = mEventData.get(position).getAddressZipcode();
        String imageUrl = mEventData.get(position).getCoverUrl();

        if(!title.isEmpty()) {
            holder.mTitle.setText(title);
        } else {
            holder.mTitle.setText(R.string.unknown);
        }

        String finalDate;
        try {
            finalDate = DateFormatter.format(startDate, endDate);
        } catch(Exception ex) {
            Timber.i("Not able to parse event dates: %s - %s", startDate, endDate);
            finalDate = mContext.getResources().getString(R.string.unknown);
        }
        holder.mDate.setText(finalDate);

        if(priceType != null && !priceType.isEmpty()) {
            holder.mType.setText(priceType);
        } else {
            holder.mType.setVisibility(View.GONE);
        }

        if(zipCode != null && !zipCode.isEmpty()) {
            holder.mZipCode.setText(zipCode);
        } else {
            holder.mZipCode.setVisibility(View.GONE);
        }

        Picasso.with(mContext)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.mCover);
    }

    @Override
    public int getItemCount() {
        return mEventData.size();
    }

    public class EventAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mCover;
        final TextView mTitle;
        final TextView mDate;
        final TextView mType;
        final TextView mZipCode;
        final ImageView mFavorite;

        EventAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCover = itemView.findViewById(R.id.event_cover);
            this.mTitle = itemView.findViewById(R.id.event_title);
            this.mDate = itemView.findViewById(R.id.event_date);
            this.mType = itemView.findViewById(R.id.event_type);
            this.mZipCode = itemView.findViewById(R.id.event_zip_code);
            this.mFavorite = itemView.findViewById(R.id.event_favorite);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mClickListener.onEventItemClick(mEventData.get(clickedPosition));
        }
    }

    public interface EventClickListener {
        void onEventItemClick(Event event);
    }
}
