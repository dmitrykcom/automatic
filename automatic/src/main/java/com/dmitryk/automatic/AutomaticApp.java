package com.dmitryk.automatic;

import android.app.Application;
import com.squareup.otto.Bus;

/**
 * Created by koroblyaker on 11/9/15.
 */
public class AutomaticApp extends Application {

  private static Bus bus;

  public static Bus bus() {
    if (bus == null) {
      bus = new Bus();
    }
    return bus;
  }
}
