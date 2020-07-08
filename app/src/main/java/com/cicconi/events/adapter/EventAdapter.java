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
import java.util.ArrayList;
import java.util.List;

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

        if(!title.isEmpty()) {
            holder.mName.setText(title);
        } else {
            holder.mName.setText(R.string.unknown);
        }

        if(mEventData.get(position).isFavorite()) {
            holder.mFavorite.setColorFilter(mContext.getResources().getColor(R.color.colorFavorite));
        }
    }

    @Override
    public int getItemCount() {
        return mEventData.size();
    }

    public class EventAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mName;
        final TextView mServings;
        final ImageView mFavorite;

        EventAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mName = itemView.findViewById(R.id.tv_name);
            this.mServings = itemView.findViewById(R.id.tv_servings_value);
            this.mFavorite = itemView.findViewById(R.id.iv_favorite);
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
