package com.marudhu.samay;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.marudhu.samay.library.OpenWeather;

import java.util.ArrayList;


public class WeatherActivity extends Activity implements LocationListener {

    LocationManager locationManager;

    /* Location info */

    //default temp in kelvin
    double currentTemp = 0.0;
    String currentCity ="";
    String tempType="cel";
    String currentCountry = "";
    String currentTempType ="";
    Double currentLattitude;
    Double currentLongitude;
    Integer currentCondCode ;

    TextView txtLocation;
    TextView txtTemp;
    TextView txtTempType;
    ImageView tempPic;

    OpenWeather openWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();
        setContentView(R.layout.activity_weather);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        try {
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 2, 10, this);
        }catch (Exception e){}

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 2, 10, this);
        }catch (Exception e){}

        openWeather = new OpenWeather();

        txtLocation = (TextView) findViewById(R.id.location);
        txtTemp = (TextView) findViewById(R.id.fullscreen_content);
        txtTempType = (TextView) findViewById(R.id.tempType);
        tempPic = (ImageView) findViewById(R.id.tempPic);

        Spinner spinnertempType = (Spinner) findViewById(R.id.degreeType);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Celsius");
        list.add("Fahrenheit");
        list.add("Kelvin");

        final ArrayAdapter adapter = new ArrayAdapter(WeatherActivity.this,R.layout.spinner_item,list);
        spinnertempType.setAdapter(adapter);

        spinnertempType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempType = adapter.getItem(i).toString();
                updateView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tempType = adapter.getItem(0).toString();
                updateView();
            }
        });

        newLocationFound(locationManager.getLastKnownLocation(LOCATION_SERVICE));
        fetchPref();
    }

    private void updateView(){

//        Toast.makeText(this,"Updating",Toast.LENGTH_SHORT).show();

        if (tempType.equals("Celsius")) { //celcius temp
            txtTemp.setText(Integer.toString((int) (currentTemp  - 273.15)) + " °");
        }else  if (tempType.equals("Fahrenheit")) { //fahrenheit temp
            txtTemp.setText(Integer.toString((int) (currentTemp  - -457.87)) + "°");
        }else{ //default kelvin temp
            txtTemp.setText(Double.toString(currentTemp) + " °");
        }

        txtLocation.setText(currentCity + ", " + currentCountry);
        txtTempType.setText(currentTempType + " in ");

        Ion.with(WeatherActivity.this)
           .load(openWeather.getImageUrl(currentCondCode))
           .withBitmap()
           .intoImageView(tempPic);



    }

    private void updatePref(){
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString("currentCity",currentCity);
        prefEditor.putString("currentCountry",currentCountry);
        prefEditor.putString("currentTemp", String.valueOf(currentTemp));
        prefEditor.putString("currentTempType",currentTempType);
        prefEditor.putInt("currentCondCode",currentCondCode);


        prefEditor.putString("currentLat", String.valueOf(currentLattitude));
        prefEditor.putString("currentLong",String.valueOf(currentLongitude.toString()));

        prefEditor.commit();
        fetchPref();
    }

    private void fetchPref(){
        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        currentCity = pref.getString("currentCity", "");
        currentCountry = pref.getString("currentCountry", "");
        currentTemp = Double.parseDouble(pref.getString("currentTemp", "0"));
        currentTempType = pref.getString("currentTempType", "");
        currentCondCode = pref.getInt("currentCondCode",200);

        currentLattitude = Double.parseDouble(pref.getString("currentLat", ""));
        currentLongitude = Double.parseDouble(pref.getString("currentLong", ""));

        updateView();
    }

    private void newLocationFound(Location location){

        if (location == null) return;

        Ion.with(WeatherActivity.this)
                .load("http://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result == null) return;

                        JsonObject main = result.getAsJsonObject("main");

                        currentTemp = main.get("temp").getAsInt();
                        currentCity = result.get("name").getAsString();

                        currentCountry = result.get("sys").getAsJsonObject().get("country").getAsString();

                        currentTempType = result.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();

                        updatePref();
                    }
                });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    };

    @Override
    public void onLocationChanged(Location location) {
        newLocationFound(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 2, 10, this);
        }catch (Exception e){}

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 2, 10, this);
        }catch (Exception e){}

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
