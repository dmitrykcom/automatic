package com.dmitryk.automatic.api;

import com.dmitryk.automatic.AutomaticApp;
import com.dmitryk.automatic.events.EventTripsReceived;
import com.dmitryk.automatic.models.Trip;
import com.dmitryk.automatic.models.Results;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.List;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by koroblyaker on 11/9/15.
 */
public class AutomaticClient {

  private static final String API_BASE_URL = "https://api.automatic.com/";
  private static final String AUTH_TOKEN = "Bearer e5cdd2a2f2c52ac2ff9825f53ac566f45c513991";

  private static AutomaticApi getApi(final String authToken) {
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();

    OkHttpClient httpClient = new OkHttpClient();

    httpClient.interceptors().add(new Interceptor() {
      @Override
      public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
            .addHeader("Authorization", authToken)
            .method(original.method(), original.body())
            .build();

        return chain.proceed(request);
      }
    });


  /* interceptor to add authtoken into header */
    AutomaticApi api = new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
        .create(AutomaticApi.class);
    return api;
  }

  public static Call<Results<Trip>> trips(int limit, int page) {
    return AutomaticClient.getApi(AUTH_TOKEN).trip(String.valueOf(limit), String.valueOf(page));
  }

  public static void tripsAsync(final int limit, final int page) {

    Observable.create(new Observable.OnSubscribe<List<Trip>>() {
      @Override public void call(Subscriber<? super List<Trip>> subscriber) {
        try {
          Call<Results<Trip>> call = trips(limit, page);
          retrofit.Response<Results<Trip>> response = call.execute();
          List<Trip> trips = response.body().getData();
          subscriber.onNext(trips);
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Trip>>() {
          @Override public void call(List<Trip> trips) {
            // onNext
            AutomaticApp.bus().post(new EventTripsReceived(trips));
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
          }
        });
  }
}
