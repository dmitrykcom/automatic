package com.dmitryk.automatic.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.dmitryk.automatic.R;
import com.dmitryk.automatic.models.Trip;
import java.util.Comparator;
import java.util.List;

/**
 * Created by koroblyaker on 11/10/15.
 */
public class TripAdapter extends ArrayAdapter {
  public TripAdapter(Context context, int resource, List objects) {
    super(context, resource, objects);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder viewHolder;

    if (convertView == null) {
      LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
      convertView = inflater.inflate(R.layout.trip_list_item, parent, false);
    }

    viewHolder = new ViewHolder(convertView);

    Trip trip = (Trip) getItem(position);

    viewHolder.startAddress.setText(getContext().getString(R.string.address_from, trip.getStartAddress().getDisplayName()));
    viewHolder.endAddress.setText(getContext().getString(R.string.address_to, trip.getEndAddress().getDisplayName()));
    viewHolder.duration.setText(getContext().getString(R.string.trip_duration, trip.getDuration()));
    viewHolder.cost.setText(getContext().getString(R.string.trip_cost, trip.getCost()));


    return convertView;
  }

  public static class ViewHolder {

    public TextView startAddress;
    public TextView endAddress;
    public TextView duration;
    public TextView cost;

    public ViewHolder(View v) {
      startAddress = (TextView) v.findViewById(R.id.start_address);
      endAddress = (TextView) v.findViewById(R.id.end_address);
      duration = (TextView) v.findViewById(R.id.duration);
      cost = (TextView) v.findViewById(R.id.cost);
    }
  }

  public static class CostComparator implements Comparator<Trip> {

    @Override public int compare(Trip o, Trip t1) {
      Float cost1 = Float.valueOf(o.getCost());
      Float cost2 = Float.valueOf(t1.getCost());
      return cost1.compareTo(cost2);
    }
  }

  public static class DistanceComparator implements Comparator<Trip> {
    @Override public int compare(Trip o, Trip t1) {
      return o.getDistance().compareTo(t1.getDistance());
    }
  }

  public static class DestinationComparator implements Comparator<Trip> {
    @Override public int compare(Trip o, Trip t1) {
      if(o.getEndAddress().getDisplayName() == null || t1.getEndAddress().getDisplayName() == null) {
        return 0;
      }
      return o.getEndAddress().getDisplayName().compareTo(t1.getEndAddress().getDisplayName());
    }
  }

}



