package com.example.myplane.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myplane.R;
import com.example.myplane.models.Airline;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class AirlineListAdapter extends BaseAdapter {
    Context context;
    List<Airline> airlines;
    LayoutInflater inflater;

    public AirlineListAdapter(Context context, List<Airline> airlines) {
        this.context = context;
        this.airlines = airlines;
        this.inflater = LayoutInflater.from(this.context);
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    @Override
    public int getCount() {
        return this.airlines.size();
    }

    @Override
    public Object getItem(int i) {
        return this.airlines.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view  = inflater.inflate(R.layout.airline_list,null);
        TextView tvAirlineName = view.findViewById(R.id.tvAirlinerName);
        TextView tvIataCode = view.findViewById(R.id.tvAirlinerIata);
        TextView tvIcaoCode = view.findViewById(R.id.tvAirlinerIcao);
        TextView tvTotalAircrafts = view.findViewById(R.id.tvTotalAircrafts);
        TextView tvAircraftsName = view.findViewById(R.id.tvAircraftName);
        TextView tvAircraftCount = view.findViewById(R.id.tvAircraftCount);
        ImageView imgAirline = view.findViewById(R.id.imgAirliner);


        Airline airline = airlines.get(i);
        Map<String,Integer> fleet = airline.getAirlineFleet();

        tvAirlineName.setText(airline.getName());
        tvIataCode.setText("IATA:"+airline.getIata());
        tvIcaoCode.setText("ICAO:"+airline.getIcao());
        tvTotalAircrafts.setText("Total Aircrafts:"+fleet.get("total"));
        StringBuilder aircrafts = new StringBuilder();
        StringBuilder counts = new StringBuilder();
        fleet.forEach((aircraft,count)->{
            if(aircraft.equals("total")){
                return;
            }
            if(aircrafts.toString().isEmpty()){
                aircrafts.append(aircraft);
                counts.append(count.toString());
            }
            else{
                aircrafts.append("\n").append(aircraft);
                counts.append("\n").append(count.toString());
            }

        });
        tvAircraftsName.setText(aircrafts.toString());
        tvAircraftCount.setText(counts.toString());

        String logo = airline.getLogo();
        if(!logo.isEmpty()){
            Picasso.get().load(logo).into(imgAirline);
        }

        return view;
    }
}
