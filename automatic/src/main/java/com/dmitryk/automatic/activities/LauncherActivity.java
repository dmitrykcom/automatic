package com.dmitryk.automatic.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.dmitryk.automatic.AutomaticApp;
import com.dmitryk.automatic.R;
import com.dmitryk.automatic.adapters.TripAdapter;
import com.dmitryk.automatic.api.AutomaticClient;
import com.dmitryk.automatic.events.EventTripsReceived;
import com.dmitryk.automatic.models.Trip;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends Activity {

  private static final int MAX_TRIPS_PER_PAGE = 15;

  private ListView tripsList;
  private TripAdapter tripAdapter;
  private List<Trip> tripsData = new ArrayList<Trip>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    tripsList = (ListView) findViewById(R.id.trips_list);
    tripAdapter = new TripAdapter(this, 0, tripsData);
    tripsList.setAdapter(tripAdapter);

    AutomaticApp.bus().register(this);

    /* request first page of trips */
    AutomaticClient.tripsAsync(MAX_TRIPS_PER_PAGE, 1);
  }

  @Override protected void onDestroy() {
    AutomaticApp.bus().unregister(this);
    super.onDestroy();
  }

  @Subscribe
  public void onTripsReceived(EventTripsReceived event) {
    tripsData.addAll(event.getTrips());
    tripAdapter.notifyDataSetChanged();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.actions, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.sort_cost:
        tripAdapter.sort(new TripAdapter.CostComparator());
        return true;

      case R.id.sort_destination:
        tripAdapter.sort(new TripAdapter.DestinationComparator());
        return true;

      case R.id.sort_distance:
        tripAdapter.sort(new TripAdapter.DistanceComparator());
        return true;

      case R.id.menu_path:
        showPaths();
        return true;

      default:
        return super.onOptionsItemSelected(item);

    }
  }

  public void showPaths() {
    Intent intent = new Intent(this, PathActivity.class);
    String[] paths = new String[tripsData.size()];
    for(int i = 0; i < paths.length; i++) {
      paths[i] = tripsData.get(i).getPath();
    }
    intent.putExtra(PathActivity.EXTRA_PATH, paths);
    startActivity(intent);
  }

}
