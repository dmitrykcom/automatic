package com.dmitryk.automatic.events;

import com.dmitryk.automatic.models.Trip;
import java.util.List;

/**
 * Created by koroblyaker on 11/9/15.
 */
public class EventTripsReceived {

  private List<Trip> trips;

  public EventTripsReceived(List<Trip> trips) {
    this.trips = trips;
  }

  public List<Trip> getTrips() {
    return trips;
  }

  public void setTrips(List<Trip> trips) {
    this.trips = trips;
  }
}
