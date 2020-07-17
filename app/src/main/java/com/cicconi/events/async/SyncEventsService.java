package com.cicconi.events.async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SyncEventsService extends Service {

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.i("Work starting");

        String action = intent.getAction();
        mCompositeDisposable = new CompositeDisposable();
        SyncEventsTask.executeTask(this, mCompositeDisposable, action);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i("Work completed");
        mCompositeDisposable.dispose();
    }
}
