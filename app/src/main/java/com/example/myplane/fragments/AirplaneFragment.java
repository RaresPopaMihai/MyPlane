package com.example.myplane.fragments;

import static com.example.myplane.activities.MainActivity.planes;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myplane.R;
import com.example.myplane.adapters.AirplaneListAdapter;
import com.example.myplane.models.HttpController;
import com.example.myplane.models.Plane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;

public class AirplaneFragment extends AbstractFragment {

    EditText etModelSearch;
    ListView lvAirplanes;
    BaseAdapter adapter;

    public AirplaneFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.URL_STRING = getString(R.string.url_airplane);
        return inflater.inflate(R.layout.fragment_airplane, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etModelSearch = view.findViewById(R.id.etSearchModel);
        lvAirplanes = view.findViewById(R.id.lvAirplane);
        adapter = new AirplaneListAdapter(getContext(),planes);
        lvAirplanes.setAdapter(adapter);
    }

    @Override
    public void searchRelevantInformation(String search) {

        String model = etModelSearch.getText().toString().trim();
        if(search.isEmpty()&& model.isEmpty()){
            Toast.makeText(getContext(),"You must provide a manufacturer or model",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"Searching planes...",Toast.LENGTH_SHORT).show();
            new airplaneFetcher().execute(search,model,URL_STRING,getString(R.string.apiKey));
        }

    }

    private class airplaneFetcher extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            planes.clear();
        }

        @Override
        protected Void doInBackground(String... strings) {

            String givenManufacturer =strings[0];
            String givenModel =strings[1];
            String url = strings[2];
            String key = strings[3];
            HttpController controller = new HttpController();
            String jsonString = "";
            try {
                jsonString = controller.call(url,key,givenManufacturer,givenModel);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if(!jsonString.isEmpty()){
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    if(planes.size()>0){
                        planes.clear();
                    }
                    for(int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonPlane = jsonArray.getJSONObject(i);
                        String manufacturer = jsonPlane.getString("manufacturer");
                        String model = jsonPlane.getString("model");
                        String engine_type = jsonPlane.getString("engine_type");
                        double maxSpeed = jsonPlane.getDouble("max_speed_knots");
                        maxSpeed = knotsToKMH(maxSpeed);
                        double ceilingAltitude = jsonPlane.getDouble("max_speed_knots");
                        ceilingAltitude = ftToM(ceilingAltitude);
                        double grossWeight = jsonPlane.getDouble("gross_weight_lbs");
                        grossWeight = lbsToKg(grossWeight);
                        double length = jsonPlane.getDouble("length_ft");
                        length = ftToM(length);
                        double height = jsonPlane.getDouble("height_ft");
                        height = ftToM(height);
                        double wingSpan = jsonPlane.getDouble("wing_span_ft");
                        wingSpan = ftToM(wingSpan);
                        double range = 0.0;

                        try{
                            //not all planes have this property set
                            range = jsonPlane.getDouble("range_nautical_miles");
                        }catch (Exception ex){

                        }
                        Plane plane = new Plane(manufacturer,model,engine_type,maxSpeed,grossWeight,
                                ceilingAltitude,length,height,wingSpan,range);
                        planes.add(plane);
                        Log.i("Success", "Plane succesfully retrieved "+plane);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e("PLANE-ERR", "Could not get planes json");
            }

            return null;
        }

        private Double lbsToKg(Double grossWeight) {
            BigDecimal bd = BigDecimal.valueOf(grossWeight/2.26);
            bd = bd.setScale(4, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        private Double ftToM(Double ceilingAltitude) {
            BigDecimal bd = BigDecimal.valueOf(ceilingAltitude* 0.3048);
            bd = bd.setScale(4, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        private Double knotsToKMH(Double maxSpeed) {
            BigDecimal bd = BigDecimal.valueOf(maxSpeed* 1.852);
            bd = bd.setScale(4, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();
            if(planes.size() == 0)
                Toast.makeText(getContext(),"No results found...",Toast.LENGTH_SHORT).show();
        }
    }

}