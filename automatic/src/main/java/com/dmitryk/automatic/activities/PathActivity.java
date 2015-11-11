package com.dmitryk.automatic.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.dmitryk.automatic.R;
import com.dmitryk.automatic.Utils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import java.util.List;

public class PathActivity extends FragmentActivity {

  private GoogleMap mMap;
  public static final String EXTRA_PATH = "extra_path";

  private String[] encodedPaths;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    encodedPaths = (String[]) getIntent().getExtras().get(EXTRA_PATH);

    setContentView(R.layout.activity_path);
    setUpMapIfNeeded();
  }

  @Override
  protected void onResume() {
    super.onResume();
    setUpMapIfNeeded();
  }

  private void setUpMapIfNeeded() {
    if (mMap == null) {
      mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
      if (mMap != null) {
        setUpMap();
      }
    }
  }

  private void setUpMap() {
    if(encodedPaths != null) {
      for (String encodedPoly: encodedPaths) {
        if(encodedPoly == null) {
          continue;
        }
        List<LatLng> decoded = PolyUtil.decode(encodedPoly);
        mMap.addPolyline(new PolylineOptions().addAll(decoded).color(Utils.randomColor()));
      }
    }
  }


}
