package com.dmitryk.automatic.api;

import com.dmitryk.automatic.models.Results;
import com.dmitryk.automatic.models.Trip;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by koroblyaker on 11/9/15.
 */
interface AutomaticApi  {

  @GET("/trip") Call<Results<Trip>> trip(@Query(value = "limit") String limit, @Query(value = "page") String page);
}
