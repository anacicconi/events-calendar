package com.cicconi.events.worker;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.cicconi.events.model.EventResponse;
import com.cicconi.events.model.RecordResponse;
import com.cicconi.events.network.RetrofitBuilder;
import com.cicconi.events.repository.EventRepository;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SyncEventsWorker extends Worker {

    private static final String TAG = SyncEventsWorker.class.getSimpleName();

    private EventRepository mEventRepository;
    private CompositeDisposable mCompositeDisposable;

    public SyncEventsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mEventRepository = new EventRepository(context);
        mCompositeDisposable = new CompositeDisposable();
    }

    @NonNull
    @Override
    public Result doWork() {
        Disposable disposable = getRemoteEvents()
            .doOnNext(i -> Log.d(TAG, String.format("Thread doWork: %s", Thread.currentThread().getName())))
            .subscribe(eventResponse -> {
                    for (RecordResponse recordResponse: eventResponse.getRecords()) {
                        getLocalEvent(recordResponse);
                    }
                },
                Throwable::printStackTrace,
                () -> Log.d(TAG, "Synch completed")
            );

        mCompositeDisposable.add(disposable);

        // TODO: succes here or on rx?
        return Result.success();

    }

    private Observable<EventResponse> getRemoteEvents() {
        return RetrofitBuilder.getClient().getEvents()
            .subscribeOn(Schedulers.io())
            .onErrorReturn(e -> getEmptyApiResponse());
    }

    private void getLocalEvent(RecordResponse recordResponse) {
        Disposable disposable = mEventRepository.getEventByApiId(recordResponse.getRecordid())
            .doOnError(error -> addEvent(recordResponse))
            .subscribe(
                localEvent -> {
                    if(localEvent == null) {
                        addEvent(recordResponse);
                    }
                },
                error -> Log.d(TAG, "New event received")
            );

        mCompositeDisposable.add(disposable);
    }

    private EventResponse getEmptyApiResponse() {
        return new EventResponse();
    }

    private void addEvent(RecordResponse recordResponse) {
        Disposable disposable = mEventRepository.addEvent(recordResponse.toEvent())
            .subscribe(eventId -> Log.d(TAG, "New event added"), Throwable::printStackTrace);

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onStopped() {
        mCompositeDisposable.dispose();
    }
}
