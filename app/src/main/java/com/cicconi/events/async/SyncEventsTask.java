package com.cicconi.events.async;

import android.content.Context;
import com.cicconi.events.Constants;
import com.cicconi.events.model.EventResponse;
import com.cicconi.events.model.RecordResponse;
import com.cicconi.events.network.RetrofitBuilder;
import com.cicconi.events.repository.EventRepository;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class SyncEventsTask {

    private static CompositeDisposable mCompositeDisposable;
    private static EventRepository mEventRepository;

    static void executeTask(Context context, CompositeDisposable compositeDisposable, String action) {
        if(action == null || action.isEmpty()) {
            Timber.w("Calling the task without an action");
            return;
        }

        mCompositeDisposable = compositeDisposable;
        mEventRepository = new EventRepository(context);

        if(action.equals(Constants.SYNCHRONIZE_EVENTS)) {
            synchronizeEvents();
        }
    }

    private static void synchronizeEvents() {
        Timber.i("Thread: %s", Thread.currentThread().getName());

        Disposable disposable = getRemoteEvents()
            .doOnNext(i -> Timber.d("Thread doWork: %s", Thread.currentThread().getName()))
            .subscribe(eventResponse -> {
                for (RecordResponse recordResponse : eventResponse.getRecords()) {
                    getLocalEvent(recordResponse);
                }
            }, Throwable::printStackTrace, () -> Timber.d("Sync completed"));

        mCompositeDisposable.add(disposable);
    }

    private static Observable<EventResponse> getRemoteEvents() {
        return RetrofitBuilder.getClient().getEvents()
            .subscribeOn(Schedulers.io())
            .onErrorReturn(e -> getEmptyApiResponse());
    }

    private static void getLocalEvent(RecordResponse recordResponse) {
        Disposable disposable = mEventRepository.getEventByApiId(recordResponse.getRecordid())
            .doOnError(error -> addEvent(recordResponse))
            .subscribe(localEvent -> {
                if (localEvent == null) {
                    addEvent(recordResponse);
                }
            }, error -> Timber.d("New event received"));

        mCompositeDisposable.add(disposable);
    }

    private static EventResponse getEmptyApiResponse() {
        return new EventResponse();
    }

    private static void addEvent(RecordResponse recordResponse) {
        Disposable disposable = mEventRepository.addEvent(recordResponse.toEvent())
            .subscribe(eventId -> Timber.d("New event added"), Throwable::printStackTrace);

        mCompositeDisposable.add(disposable);
    }
}
