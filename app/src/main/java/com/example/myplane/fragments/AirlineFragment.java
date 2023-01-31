package com.example.myplane.fragments;

import static com.example.myplane.activities.MainActivity.airlines;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myplane.R;
import com.example.myplane.adapters.AirlineListAdapter;
import com.example.myplane.models.Airline;
import com.example.myplane.models.HttpController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AirlineFragment extends AbstractFragment {

AirlineListAdapter adapter ;
ListView lvAirlines;
    public AirlineFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.URL_STRING = getString(R.string.url_airline);
        return inflater.inflate(R.layout.fragment_airline, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvAirlines = getView().findViewById(R.id.lvAirliner);
        Log.i("Info","Airline list size"+airlines.size());
        adapter = new AirlineListAdapter(getContext(),airlines);
        lvAirlines.setAdapter(adapter);
    }

    @Override
    public void searchRelevantInformation(String search) {
        Log.i("Search",search);
        if(search.isEmpty()){
            Toast.makeText(getContext(),"You must provide an airline name!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"Searching airlines...",Toast.LENGTH_SHORT).show();
            new AirlineFetcher().execute(search,URL_STRING,getString(R.string.apiKey));
        }


    }

    private class AirlineFetcher extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            airlines.clear();
        }

        @Override
        protected Void doInBackground(String... args) {
            String company =args[0];
            String url = args[1];
            String key = args[2];
            HttpController controller = new HttpController();
            String jsonString = "";
            try {
                jsonString = controller.call(url,key,company);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if(!jsonString.isEmpty()){
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    if(airlines.size()>0){
                        airlines.clear();
                    }
                    for(int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonAirliner = jsonArray.getJSONObject(i);
                        String name = jsonAirliner.getString("name");
                        String iata = jsonAirliner.getString("iata");
                        String icao = jsonAirliner.getString("icao");
                        String pictureUrl = "";
                        try{
                            // not all airlines have logos
                            pictureUrl = jsonAirliner.getString("logo_url");
                        }catch (Exception ex){
                            Log.i("INFO", "Airline "+ name + " does not have logo");
                        }

                        JSONObject jsonFleet = jsonAirliner.getJSONObject("fleet");
                        Map<String, Integer> airlineFleet = new HashMap<>();
                        Iterator<String> keys = jsonFleet.keys();
                        while (keys.hasNext()){
                            String aircraftModel = keys.next();
                            Integer aircraftModelCount = Integer.parseInt(jsonFleet.getString(aircraftModel));
                            airlineFleet.put(aircraftModel,aircraftModelCount);
                        }
                        Airline airline = new Airline(airlineFleet,name,iata,icao);
                        if(!pictureUrl.isEmpty())
                            airline.setLogo(pictureUrl);
                        Log.i("SUCCESS","Airline added! "+airline);
                        airlines.add(airline);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e("AIRLINE-ERR", "Could not get json");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();
            if(airlines.size()==0)
                Toast.makeText(getContext(),"No airlines found...",Toast.LENGTH_SHORT).show();
        }
    }
}