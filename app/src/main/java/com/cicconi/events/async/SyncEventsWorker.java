package com.cicconi.events.async;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.cicconi.events.Constants;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SyncEventsWorker extends Worker {
    
    private Context mContext;
    private CompositeDisposable mCompositeDisposable;

    public SyncEventsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
        mCompositeDisposable = new CompositeDisposable();
    }

    @NonNull
    @Override
    public Result doWork() {
        Timber.i("Work starting");
        SyncEventsTask.executeTask(mContext, mCompositeDisposable, Constants.SYNCHRONIZE_EVENTS);

        return Result.success();
    }

    @Override
    public void onStopped() {
        Timber.i("Work completed");
        mCompositeDisposable.dispose();
    }
}
