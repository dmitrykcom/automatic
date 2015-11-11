package com.dmitryk.automatic;

import android.graphics.Color;
import java.util.Random;

/**
 * Created by koroblyaker on 11/10/15.
 */
public class Utils {

  private Utils() {
  }

  public static int randomColor() {
    Random rand = new Random();
    int r = rand.nextInt(255);
    int g = rand.nextInt(255);
    int b = rand.nextInt(255);
    int randomColor = Color.rgb(r, g, b);
    return randomColor;
  }
}
