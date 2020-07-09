package com.cicconi.events.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cicconi.events.R;
import com.cicconi.events.database.Event;
import com.cicconi.events.utils.ColorHandler;
import com.cicconi.events.utils.DateFormatter;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
        Long dateStart = mEventData.get(position).getDateStart();
        Long dateEnd = mEventData.get(position).getDateEnd();
        String priceType = mEventData.get(position).getPriceType();
        String imageUrl = mEventData.get(position).getCoverUrl();

        if(!title.isEmpty()) {
            holder.mTitle.setText(title);
        } else {
            holder.mTitle.setText(R.string.unknown);
        }

        String finalDate = String.format("%s - %s", DateFormatter.format(mContext, dateStart),
            DateFormatter.format(mContext, dateEnd));
        holder.mDate.setText(finalDate);

        if(priceType != null && !priceType.isEmpty()) {
            holder.mType.setText(priceType);
        } else {
            holder.mType.setText(R.string.unknown);
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
        final ImageView mFavorite;

        EventAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCover = itemView.findViewById(R.id.event_cover);
            this.mTitle = itemView.findViewById(R.id.event_title);
            this.mDate = itemView.findViewById(R.id.event_date);
            this.mType = itemView.findViewById(R.id.event_type);
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
