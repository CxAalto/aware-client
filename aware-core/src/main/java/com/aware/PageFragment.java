package com.aware;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by researcher on 19/10/15.
 */
public class PageFragment extends AppCompatDialogFragment {

    private int theLayout;
    private int step;
    private Context context;
    private JSONArray jsonArray;
    private List<Sensor_Card_Info> sensors;
    private boolean empty;
    private boolean accesibilityNedded;

    public PageFragment(int nLayout, int step, JSONArray jsonArray)
    {
        empty = false;
        accesibilityNedded = false;
        theLayout = nLayout;
        this.step = step;
        this.jsonArray = jsonArray;
        context = getActivity();

        switch(step)
        {
            case 0:
                if(jsonArray.length()==0)
                {
                    empty = true;
                }
                else
                {
                    constructSensorCards();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                theLayout, container, false);

        switch(step){
            case 0:
                if(empty)
                {
                    rootView.findViewById(R.id.data_gathered_label).setVisibility(View.INVISIBLE);
                }
                else
                {
                    GridView gridView = (GridView) rootView.findViewById(R.id.sensors_gathered);
                    gridView.setAdapter(new Sensor_Adapter(context, 0, sensors));
                    if(accesibilityNedded)
                    {
                        rootView.findViewById(R.id.accesibility_message).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        rootView.findViewById(R.id.accesibility_message).setVisibility(View.GONE);
                    }
                }
                break;
        }

        return rootView;
    }

    private void constructSensorCards()
    {
        sensors = new ArrayList<>();
        for( int i=0; i < jsonArray.length(); i++ ) {
            try {
                JSONObject sensor_config = jsonArray.getJSONObject(i);
                sensors.add(new Sensor_Card_Info(sensor_config.getString("key")));
                if(sensor_config.getString("key").equalsIgnoreCase(Aware_Preferences.STATUS_APPLICATIONS) ||
                        sensor_config.getString("key").equalsIgnoreCase(Aware_Preferences.STATUS_NOTIFICATIONS) ||
                        sensor_config.getString("key").equalsIgnoreCase(Aware_Preferences.STATUS_CRASHES) ||
                        sensor_config.getString("key").equalsIgnoreCase(Aware_Preferences.STATUS_INSTALLATIONS))
                {
                    accesibilityNedded = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //TODO: handle error
            }
        }
    }

    private class Sensor_Adapter extends ArrayAdapter<Sensor_Card_Info>
    {
        private List<Sensor_Card_Info> sensors;

        public Sensor_Adapter(Context context, int resource, List<Sensor_Card_Info> objects) {
            super(context, resource, objects);
            sensors = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent){

            // assign the view we are converting to a local variable
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.sensor_item, null);
            }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
            Sensor_Card_Info i = sensors.get(position);

            if (i != null) {

                // This is how you obtain a reference to the TextViews.
                // These TextViews are created in the XML files we defined.

                ImageView image = (ImageView) v.findViewById(R.id.sensor_image);

                // check to see if each individual textview is null.
                // if not, assign some text!
                if (image != null){
                    image.setImageResource(i.getResource());
                }
            }

            // the view must be returned to our activity
            return v;

        }

    }

    private class Sensor_Card_Info {

        private int resource;

        public Sensor_Card_Info(String sensor)
        {
            if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_ACCELEROMETER))
            {
                resource = R.drawable.ic_action_accelerometer;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_APPLICATIONS) || sensor.equalsIgnoreCase(Aware_Preferences.STATUS_NOTIFICATIONS) || sensor.equalsIgnoreCase(Aware_Preferences.STATUS_CRASHES))
            {
                resource = R.drawable.ic_action_applications;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_BAROMETER))
            {
                resource = R.drawable.ic_action_barometer;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_BATTERY))
            {
                resource = R.drawable.ic_action_battery;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_BLUETOOTH))
            {
                resource = R.drawable.ic_action_bluetooth;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_COMMUNICATION_EVENTS) || sensor.equalsIgnoreCase(Aware_Preferences.STATUS_CALLS) || sensor.equalsIgnoreCase(Aware_Preferences.STATUS_MESSAGES))
            {
                resource = R.drawable.ic_action_communication;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_ESM))
            {
                resource = R.drawable.ic_action_esm;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_GRAVITY))
            {
                resource = R.drawable.ic_action_gravity;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_GYROSCOPE))
            {
                resource = R.drawable.ic_action_gyroscope;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_INSTALLATIONS))
            {
                resource = R.drawable.ic_action_installations;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_KEYBOARD))
            {
                resource = R.drawable.ic_action_keyboard;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_LIGHT))
            {
                resource = R.drawable.ic_action_light;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_LINEAR_ACCELEROMETER))
            {
                resource = R.drawable.ic_action_linear_acceleration;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_LOCATION_GPS) || sensor.equalsIgnoreCase(Aware_Preferences.STATUS_LOCATION_NETWORK))
            {
                resource = R.drawable.ic_action_locations;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_MAGNETOMETER))
            {
                resource = R.drawable.ic_action_magnetometer;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_MQTT))
            {
                resource = R.drawable.ic_action_mqtt;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_NETWORK_EVENTS) || sensor.equalsIgnoreCase(Aware_Preferences.STATUS_NETWORK_TRAFFIC))
            {
                resource = R.drawable.ic_action_network;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_PROCESSOR))
            {
                resource = R.drawable.ic_action_processor;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_PROXIMITY))
            {
                resource = R.drawable.ic_action_proximity;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_ROTATION))
            {
                resource = R.drawable.ic_action_rotation;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_SCREEN))
            {
                resource = R.drawable.ic_action_screen;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_TELEPHONY))
            {
                resource = R.drawable.ic_action_telephony;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_TEMPERATURE))
            {
                resource = R.drawable.ic_action_temperature;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_TIMEZONE))
            {
                resource = R.drawable.ic_action_timezone;
            }
            else if(sensor.equalsIgnoreCase(Aware_Preferences.STATUS_WIFI))
            {
                resource = R.drawable.ic_action_wifi;
            }
        }

        public int getResource()
        {
            return resource;
        }

    }
}
