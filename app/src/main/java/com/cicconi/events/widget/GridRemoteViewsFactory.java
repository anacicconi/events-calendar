package com.cicconi.events.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.cicconi.events.Constants;
import com.cicconi.events.R;
import com.cicconi.events.database.Event;
import com.cicconi.events.repository.EventRepository;
import com.cicconi.events.utils.DateFormatter;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private EventRepository mEventRepository;
    private List<Event> mTodayEvents = new ArrayList<>();

    public GridRemoteViewsFactory(Context context) {
        mContext = context;
        mEventRepository = new EventRepository(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mTodayEvents = mEventRepository.getTodayEventsForWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mTodayEvents.isEmpty()) return 0;
        return mTodayEvents.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mTodayEvents == null || mTodayEvents.size() == 0) return null;

        Event todayEvent = mTodayEvents.get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.event_app_widget_grid_item);
        views.setTextViewText(R.id.event_title, todayEvent.getTitle());

        String finalDate;
        try {
            finalDate = DateFormatter.format(todayEvent.getDateStart(), todayEvent.getDateEnd());
        } catch(Exception ex) {
            Timber.i("Not able to parse event dates: %s - %s", todayEvent.getDateStart(), todayEvent.getDateEnd());
            finalDate = mContext.getResources().getString(R.string.unknown);
        }

        views.setTextViewText(R.id.event_date, finalDate);
        views.setTextViewText(R.id.event_type, todayEvent.getPriceType());
        views.setTextViewText(R.id.event_zip_code, todayEvent.getAddressZipcode());

        try {
            Bitmap bitmap = Picasso.with(mContext)
                .load(todayEvent.getCoverUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .get();
            views.setImageViewBitmap(R.id.event_cover, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(Constants.EXTRA_EVENT, todayEvent);
        views.setOnClickFillInIntent(R.id.app_widget_layout, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
