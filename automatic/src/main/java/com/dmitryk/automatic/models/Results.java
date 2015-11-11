package com.dmitryk.automatic.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by koroblyaker on 11/10/15.
 */

/**
 * Wrapper for "results" field in Json response.
 * @param <K> - Model class
 */

public class Results<K> {
  @SerializedName("results")
  private List<K> data;

  public List<K> getData() {
    return data;
  }
}
