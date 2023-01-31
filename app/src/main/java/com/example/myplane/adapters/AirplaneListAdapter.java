package com.example.myplane.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myplane.R;
import com.example.myplane.models.Plane;

import java.util.List;
import java.util.Locale;

public class AirplaneListAdapter extends BaseAdapter {

    Context context;
    List<Plane> planes;
    LayoutInflater inflater;

    public AirplaneListAdapter(Context context, List<Plane> planes) {
        this.context = context;
        this.planes = planes;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.planes.size();
    }

    @Override
    public Plane getItem(int i) {
        return this.planes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view  = inflater.inflate(R.layout.plane_list,null);
        TextView tvManufacturer = view.findViewById(R.id.tvManufacturer);
        TextView tvModel = view.findViewById(R.id.tvModel);
        TextView tvEngine = view.findViewById(R.id.tvEngine);
        TextView tvMaxSpeed = view.findViewById(R.id.tvMaxSpeed);
        TextView tvCeilingAltitude = view.findViewById(R.id.tvCeilingAltitude);
        TextView tvRange = view.findViewById(R.id.tvRange);
        TextView tvGrossWeight = view.findViewById(R.id.tvGrossWeight);
        TextView tvLength = view.findViewById(R.id.tvLength);
        TextView tvHeight = view.findViewById(R.id.tvHeight);
        TextView tvWingSpan = view.findViewById(R.id.tvWingSpan);
        ImageView imgManufacturer = view.findViewById(R.id.imgManufacturer);

        Plane plane = getItem(i);
        tvManufacturer.setText(plane.getManufacturer().toUpperCase(Locale.ROOT));
        tvModel.setText(plane.getModel().toUpperCase(Locale.ROOT));
        tvEngine.setText("Engine:"+plane.getEngineType());
        tvMaxSpeed.setText("Max Speed:"+plane.getMaxSpeed()+"Km/h");
        tvCeilingAltitude.setText("Ceiling Altitude: " + plane.getCeilingAltitude() + " Meters");
        if(plane.getRange() == 0.0){
            tvRange.setText("Range:Not specified");
        }else{
            tvRange.setText("Range:"+plane.getRange()+" Nautical Miles");
        }

        tvGrossWeight.setText("Gross Weight: "+plane.getGrossWeight()+" Kg");
        tvLength.setText("Lenght:"+plane.getLength()+" Meters");
        tvHeight.setText("Height:"+plane.getHeight()+" Meters");
        tvWingSpan.setText("Wing Span:"+plane.getWingSpan()+" Meters");

        //added logos only for main manufacturers - logos api does not work very well...
        if(plane.getManufacturer().trim().toUpperCase(Locale.ROOT).equals("BOEING")){
            imgManufacturer.setImageResource(R.drawable.boeing);
        }else if(plane.getManufacturer().trim().toUpperCase(Locale.ROOT).equals("AIRBUS")){
            imgManufacturer.setImageResource(R.drawable.airbus);
        }
        else if(plane.getManufacturer().trim().toUpperCase(Locale.ROOT).contains("BOMBARDIER")){
            imgManufacturer.setImageResource(R.drawable.bombardier);
        }
        else if(plane.getManufacturer().trim().toUpperCase(Locale.ROOT).contains("LOCKHEED")){
            imgManufacturer.setImageResource(R.drawable.lockheedmartin);
        }


        return view;
    }
}
