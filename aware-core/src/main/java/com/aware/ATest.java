package com.aware;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.*;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by researcher on 07/10/15.
 */
public class ATest extends Activity {

    private View download, install, setup, last, downloadC;
    private CheckBox installC, setupC;
    private ViewGroup scenesGroupRoot, downloadV, installV, setupV, lastV, counterRoot;
    private TransitionManager mTransitionManager;
    private int counter;
    private Context context;

    private View textBackground;

    private JSONArray configs_study;

    private Join_Study_Receiver generalReceiver = new Join_Study_Receiver();


    private TextSwitcher mDownloadSwitcher, mInstallSwitcher, mSetupSwitcher, textProgress, textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.aware_study_setup);
//        try {
//            configs_study = new JSONArray(getIntent().getStringExtra("configs_study"));
//        } catch (JSONException e) {
//            Toast.makeText(context, "Error getting the study",Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        scenesGroupRoot = (ViewGroup) findViewById(R.id.container);
//        counterRoot = (ViewGroup) findViewById(R.id.counter_container);
//
//        counter = 0;
//
//        mDownloadSwitcher = (TextSwitcher) findViewById(R.id.download_text);
//        mInstallSwitcher = (TextSwitcher) findViewById(R.id.install_text);
//        mSetupSwitcher = (TextSwitcher) findViewById(R.id.setup_text);
//        textProgress = (TextSwitcher) findViewById(R.id.progress);
//        textTotal = (TextSwitcher) findViewById(R.id.total);
//        textBackground = findViewById(R.id.text_background);
//
//
//        ((TextView) findViewById(R.id.checkmark)).setText(Html.fromHtml("&#x2713;"));
//
//        mDownloadSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
//
//            public View makeView() {
//                TextView myText = new TextView(ATest.this);
//                myText.setGravity(Gravity.CENTER_VERTICAL);
//                return myText;
//            }
//        });
//        mInstallSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
//
//            public View makeView() {
//                TextView myText = new TextView(ATest.this);
//                myText.setGravity(Gravity.CENTER_VERTICAL);
//                return myText;
//            }
//        });
//        mSetupSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
//
//            public View makeView() {
//                TextView myText = new TextView(ATest.this);
//                myText.setGravity(Gravity.CENTER_VERTICAL);
//                return myText;
//            }
//        });

        textProgress.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater thisInflater = LayoutInflater.from(context);
                TextView textView = (TextView) thisInflater.inflate(R.layout.new_text, null);
                return textView;
            }
        });

        textTotal.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater thisInflater = LayoutInflater.from(context);
                TextView textView = (TextView) thisInflater.inflate(R.layout.new_text, null);
                return textView;

            }
        });

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        Animation out_up = AnimationUtils.loadAnimation(this,R.anim.slide_out_up);
        Animation in_up = AnimationUtils.loadAnimation(this,R.anim.slide_in_up);

        textProgress.setInAnimation(in_up);
        textProgress.setOutAnimation(out_up);
        textProgress.setText("1");

        textTotal.setInAnimation(in_up);
        textTotal.setOutAnimation(out_up);
        textTotal.setText("/3");


        mDownloadSwitcher.setInAnimation(in);
        mDownloadSwitcher.setOutAnimation(out);
        mDownloadSwitcher.setText(getString(R.string.aware_download_text));

        mInstallSwitcher.setInAnimation(in);
        mInstallSwitcher.setOutAnimation(out);
        mInstallSwitcher.setText(getString(R.string.aware_install_text));

        mSetupSwitcher.setInAnimation(in);
        mSetupSwitcher.setOutAnimation(out);
        mSetupSwitcher.setText(getString(R.string.aware_setup_text));



        TransitionInflater inflater = TransitionInflater.from(this);

//        download = new Scene(scenesGroupRoot, (ViewGroup) LayoutInflater.from(this).inflate(R.layout.aware_study_setup_download,scenesGroupRoot,false));
//        install = new Scene(scenesGroupRoot, (ViewGroup) LayoutInflater.from(this).inflate(R.layout.aware_study_setup_install,scenesGroupRoot,false));
////        download = Scene.getSceneForLayout(scenesGroupRoot, R.layout.aware_study_setup_download, this);
////        install = Scene.getSceneForLayout(scenesGroupRoot, R.layout.aware_study_setup_install, this);
//        setup = Scene.getSceneForLayout(scenesGroupRoot, R.layout.aware_study_setup_setup, this);
//        last = Scene.getSceneForLayout(scenesGroupRoot, R.layout.aware_study_setup_finish, this);
//        mTransitionManager = inflater.inflateTransitionManager(R.transition.transitions_mgr, scenesGroupRoot);
        final Transition defaultTransition = inflater.inflateTransition(R.transition.default_transition);
        final Transition counterTransition = inflater.inflateTransition(R.transition.counter_transition);

        download = LayoutInflater.from(context).inflate(R.layout.new_download, null);
        install = LayoutInflater.from(context).inflate(R.layout.new_install, null);
        setup = LayoutInflater.from(context).inflate(R.layout.new_setup, null);
        last = LayoutInflater.from(context).inflate(R.layout.new_finish, null);
        downloadC = LayoutInflater.from(context).inflate(R.layout.new_checkbox, null);
//        downloadC = LayoutInflater.from(context).inflate(R.layout.new_checkbox, null);
//        downloadC = LayoutInflater.from(context).inflate(R.layout.new_checkbox, null);
//        installC = (CheckBox) findViewById(R.id.install_check);
//        setupC = (CheckBox) findViewById(R.id.setup_check);
//
//        downloadV = (ViewGroup) findViewById(R.id.download_ids);
//        installV = (ViewGroup) findViewById(R.id.install_ids);
//        setupV = (ViewGroup) findViewById(R.id.final_setup_ids);
//        lastV = (ViewGroup) findViewById(R.id.final_finish_ids);
//
//        downloadV.addView(download);
//
//
//        ((Button) findViewById(R.id.toggle_button)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (counter) {
//                    case 0:
//                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
//                        textProgress.setText("2");
//                        downloadV.removeView(download);
//                        ((ViewGroup) findViewById(R.id.download_id_text)).addView(downloadC);
//                        ((ViewGroup) findViewById(R.id.install_id_text)).removeView(installC);
//                        installV.addView(install);
//                        mDownloadSwitcher.setText(getString(R.string.aware_download_text_done));
////                        TransitionManager.go(install, defaultTransition);
////                        mDownloadSwitcher.setText("1. Downloads complete!");
////                        mTransitionManager.transitionTo(install);
//                        counter++;
//                        break;
//                    case 1:
//                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
//                        textProgress.setText("3");
//                        installV.removeView(install);
//                        ((ViewGroup) findViewById(R.id.install_id_text)).addView(installC);
//                        installC.setChecked(true);
//                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).removeView(setupC);
//                        setupV.addView(setup);
//                        mInstallSwitcher.setText(getString(R.string.aware_install_text_done));
////                        TransitionManager.go(setup, defaultTransition);
////                        mTransitionManager.transitionTo(setup);
//                        counter++;
//                        break;
//                    case 2:
//                        findViewById(R.id.checkmark).setVisibility(View.VISIBLE);
//                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
//                        TransitionManager.beginDelayedTransition(counterRoot, counterTransition);
//                        ViewGroup.LayoutParams params = textBackground.getLayoutParams();
//                        params.width = getResources().getDimensionPixelSize(R.dimen.small_circle_size);
//                        textBackground.setLayoutParams(params);
//
//                        textProgress.setText(Html.fromHtml(""));
//                        textTotal.setText("");
//                        setupV.removeView(setup);
//                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).addView(setupC);
//                        setupC.setChecked(true);
//                        lastV.addView(last);
//                        mSetupSwitcher.setText(getString(R.string.aware_setup_text_done));
////                        TransitionManager.go(last, defaultTransition);
////                        mTransitionManager.transitionTo(last);
//                        counter++;
//                        break;
//                    case 3:
//                        RelativeLayout.LayoutParams theParams = (RelativeLayout.LayoutParams) textBackground.getLayoutParams();
//                        theParams.height = getResources().getDimensionPixelSize(R.dimen.small_circle_size);
//                        theParams.width = getResources().getDimensionPixelSize(R.dimen.normal_circle_size);
//                        textBackground.setLayoutParams(theParams);
//
//                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
//                        TransitionManager.beginDelayedTransition(counterRoot, counterTransition);
//
//                        theParams.height = getResources().getDimensionPixelSize(R.dimen.normal_circle_size);
//                        textBackground.setLayoutParams(theParams);
//                        findViewById(R.id.checkmark).setVisibility(View.INVISIBLE);
//
//                        textProgress.setText("3");
//                        textTotal.setText("/3");
//                        setupV.addView(setup);
//                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).removeView(setupC);
//                        setupC.setChecked(false);
//                        lastV.removeView(last);
//                        mSetupSwitcher.setText(getString(R.string.aware_setup_text));
////                        TransitionManager.go(setup, defaultTransition);
////                        mTransitionManager.transitionTo(setup);
//                        counter++;
//                        break;
//                    case 4:
//                        findViewById(R.id.checkmark).setVisibility(View.VISIBLE);
//                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
//                        textProgress.setText("2");
//                        installV.addView(install);
//                        ((ViewGroup) findViewById(R.id.install_id_text)).removeView(installC);
//                        installC.setChecked(false);
//                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).addView(setupC);
//                        setupV.removeView(setup);
//                        mInstallSwitcher.setText(getString(R.string.aware_install_text));
////                        TransitionManager.go(install, defaultTransition);
////                        mTransitionManager.transitionTo(install);
//                        counter++;
//                        break;
//                    case 5:
//                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
//                        textProgress.setText("1");
//                        downloadV.addView(download);
//                        ((ViewGroup) findViewById(R.id.download_id_text)).removeView(downloadC);
//                        ((ViewGroup) findViewById(R.id.install_id_text)).addView(installC);
//                        installV.removeView(install);
//                        mDownloadSwitcher.setText(getString(R.string.aware_download_text));
////                        TransitionManager.go(download, defaultTransition);
////                        mDownloadSwitcher.setText(getString(R.string.aware_download_text));
////                        mTransitionManager.transitionTo(download);
//                        counter = 0;
//                        break;
//                }
//            }
//        });
    }

    private void download()
    {
        downloadPlugins(this, configs_study);
    }

    private void install()
    {
        //TODO: Incorporate transitions
        installPlugins(this);
    }

    private void configure()
    {
        //TODO: Incorporate transitions
        configure(this, configs_study);
    }


    /**
     * Sets first all the settings to the client.
     * If there are plugins, apply the same settings to them.
     * This allows us to add plugins to studies from the dashboard.
     * @param context
     */
    private void downloadPlugins( Context context, JSONArray configs) {

        boolean is_developer = Aware.getSetting(context, Aware_Preferences.DEBUG_FLAG).equals("true");

        //First reset the client to default settings...
        Aware.reset(context);

        if( is_developer ) Aware.setSetting(context, Aware_Preferences.DEBUG_FLAG, true);

        //Now apply the new settings
        JSONArray plugins = new JSONArray();
        JSONArray sensors = new JSONArray();

        for( int i = 0; i<configs.length(); i++ ) {
            try {
                JSONObject element = configs.getJSONObject(i);
                if( element.has("plugins") ) {
                    plugins = element.getJSONArray("plugins");
                }
                if( element.has("sensors")) {
                    sensors = element.getJSONArray("sensors");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //Set the plugins' settings now
        ArrayList<String> active_plugins = new ArrayList<>();
        for( int i=0; i < plugins.length(); i++ ) {
            try{
                JSONObject plugin_config = plugins.getJSONObject(i);

                String package_name = plugin_config.getString("plugin");
                active_plugins.add(package_name);
            }catch( JSONException e ) {
                e.printStackTrace();
            }
        }

        //Register Receiver to get download finished
        IntentFilter filter = new IntentFilter();
        filter.addAction(Aware.AWARE_DOWNLOADS_FAILED);
        filter.addAction(Aware.AWARE_DOWNLOADS_FINISHED);
        context.registerReceiver(generalReceiver, filter);

        //Now check plugins
        new Aware_Preferences.CheckPlugins(context, true).execute(active_plugins);

    }

    /**
     * Sets first all the settings to the client.
     * If there are plugins, apply the same settings to them.
     * This allows us to add plugins to studies from the dashboard.
     * @param context
     */
    private void installPlugins( Context context) {



        boolean is_developer = Aware.getSetting(context, Aware_Preferences.DEBUG_FLAG).equals("true");

        if( is_developer ) Aware.setSetting(context, Aware_Preferences.DEBUG_FLAG, true);

        if(Aware.AWARE_PLUGIN_JOIN_INSTALL_PATHS.size() > 0)
        {
            String filePath = Aware.AWARE_PLUGIN_JOIN_INSTALL_PATHS.get(0);
            //Installs the plugin that finished downloading
            if( Aware.DEBUG ) Log.d(Aware.TAG, "Plugin to install: " + filePath);

            File mFile = new File( Uri.parse(filePath).getPath() );
            if( ! Aware.is_watch(context) ) {
                Intent promptInstall = new Intent(Intent.ACTION_VIEW);
                promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                promptInstall.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
                this.startActivityForResult(promptInstall, 5000);
                //TODO: Receiver to handle installs one by one, on receive, remove value and call method again until it's 0
            }
        }
        else
        {
            //TODO: transition
        }
    }

    /**
     * Sets first all the settings to the client.
     * If there are plugins, apply the same settings to them.
     * This allows us to add plugins to studies from the dashboard.
     * @param context
     * @param configs
     */
    private static void configure( Context context, JSONArray configs) {

        boolean is_developer = Aware.getSetting(context, Aware_Preferences.DEBUG_FLAG).equals("true");


        if( is_developer ) Aware.setSetting(context, Aware_Preferences.DEBUG_FLAG, true);

        //Now apply the new settings
        JSONArray plugins = new JSONArray();
        JSONArray sensors = new JSONArray();

        for( int i = 0; i<configs.length(); i++ ) {
            try {
                JSONObject element = configs.getJSONObject(i);
                if( element.has("plugins") ) {
                    plugins = element.getJSONArray("plugins");
                }
                if( element.has("sensors")) {
                    sensors = element.getJSONArray("sensors");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Set the sensors' settings first
        for( int i=0; i < sensors.length(); i++ ) {
            try {
                JSONObject sensor_config = sensors.getJSONObject(i);
                Aware.setSetting( context, sensor_config.getString("setting"), sensor_config.get("value") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Set the plugins' settings now
        ArrayList<String> active_plugins = new ArrayList<>();
        for( int i=0; i < plugins.length(); i++ ) {
            try{
                JSONObject plugin_config = plugins.getJSONObject(i);

                String package_name = plugin_config.getString("plugin");

                JSONArray plugin_settings = plugin_config.getJSONArray("settings");
                for(int j=0; j<plugin_settings.length(); j++) {
                    JSONObject plugin_setting = plugin_settings.getJSONObject(j);
                    Aware.setSetting(context, plugin_setting.getString("setting"), plugin_setting.get("value"), package_name);
                }
            }catch( JSONException e ) {
                e.printStackTrace();
            }
        }

        //Send data to server
        Intent sync = new Intent(Aware.ACTION_AWARE_SYNC_DATA);
        context.sendBroadcast(sync);


        //Show notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_action_aware_studies);
        mBuilder.setContentTitle("AWARE");
        mBuilder.setContentText("Thanks for joining the study!");
        mBuilder.setAutoCancel(true);

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.notify(33, mBuilder.build());
    }

    public class Join_Study_Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent.getAction().equals(Aware.AWARE_DOWNLOADS_FAILED)) {
                //TODO: Show failed message and retry
                unregisterReceiver(generalReceiver);
            }
            else if(intent.getAction().equals(Aware.AWARE_DOWNLOADS_FINISHED))
            {
                //TODO: transition to install
                unregisterReceiver(generalReceiver);
            }

        }

    }

}
