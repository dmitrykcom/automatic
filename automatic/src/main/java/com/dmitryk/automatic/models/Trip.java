package com.dmitryk.automatic.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by koroblyaker on 11/9/15.
 */
public class Trip {

  @SerializedName("fuel_cost_usd")
  private String cost;
  private String path;
  @SerializedName("distance_m")
  private String distance;
  @SerializedName("duration_s")
  private String duration;
  private Address startAddress;
  private Address endAddress;

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public Address getStartAddress() {
    return startAddress;
  }

  public void setStartAddress(Address startAddress) {
    this.startAddress = startAddress;
  }

  public Address getEndAddress() {
    return endAddress;
  }

  public void setEndAddress(Address endAddress) {
    this.endAddress = endAddress;
  }
}
