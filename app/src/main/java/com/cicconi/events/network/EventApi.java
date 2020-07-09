package com.cicconi.events.network;

import com.cicconi.events.model.EventResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface EventApi {

    @GET("api/records/1.0/search/?dataset=que-faire-a-paris-&rows=500&q=&facet=category&facet=tags&facet=address_zipcode&facet=address_city&facet=pmr&facet=blind&facet=deaf&facet=access_type&facet=price_type")
    Observable<EventResponse> getEvents();
}
