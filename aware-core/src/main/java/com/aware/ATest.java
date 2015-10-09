package com.aware;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.*;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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


    private TextSwitcher mDownloadSwitcher, mInstallSwitcher, mSetupSwitcher, textProgress, textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.aware_study_setup);
        scenesGroupRoot = (ViewGroup) findViewById(R.id.container);
        counterRoot = (ViewGroup) findViewById(R.id.counter_container);

        counter = 0;

        mDownloadSwitcher = (TextSwitcher) findViewById(R.id.download_text);
        mInstallSwitcher = (TextSwitcher) findViewById(R.id.install_text);
        mSetupSwitcher = (TextSwitcher) findViewById(R.id.setup_text);
        textProgress = (TextSwitcher) findViewById(R.id.progress);
        textTotal = (TextSwitcher) findViewById(R.id.total);
        textBackground = findViewById(R.id.text_background);


        ((TextView) findViewById(R.id.checkmark)).setText(Html.fromHtml("&#x2713;"));

        mDownloadSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(ATest.this);
                myText.setGravity(Gravity.CENTER_VERTICAL);
                return myText;
            }
        });
        mInstallSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(ATest.this);
                myText.setGravity(Gravity.CENTER_VERTICAL);
                return myText;
            }
        });
        mSetupSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(ATest.this);
                myText.setGravity(Gravity.CENTER_VERTICAL);
                return myText;
            }
        });

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
        downloadC = LayoutInflater.from(context).inflate(R.layout.new_checkbox, null);
        downloadC = LayoutInflater.from(context).inflate(R.layout.new_checkbox, null);
        installC = (CheckBox) findViewById(R.id.install_check);
        setupC = (CheckBox) findViewById(R.id.setup_check);

        downloadV = (ViewGroup) findViewById(R.id.download_ids);
        installV = (ViewGroup) findViewById(R.id.install_ids);
        setupV = (ViewGroup) findViewById(R.id.final_setup_ids);
        lastV = (ViewGroup) findViewById(R.id.final_finish_ids);

        downloadV.addView(download);


        ((Button) findViewById(R.id.toggle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (counter) {
                    case 0:
                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
                        textProgress.setText("2");
                        downloadV.removeView(download);
                        ((ViewGroup) findViewById(R.id.download_id_text)).addView(downloadC);
                        ((ViewGroup) findViewById(R.id.install_id_text)).removeView(installC);
                        installV.addView(install);
                        mDownloadSwitcher.setText(getString(R.string.aware_download_text_done));
//                        TransitionManager.go(install, defaultTransition);
//                        mDownloadSwitcher.setText("1. Downloads complete!");
//                        mTransitionManager.transitionTo(install);
                        counter++;
                        break;
                    case 1:
                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
                        textProgress.setText("3");
                        installV.removeView(install);
                        ((ViewGroup) findViewById(R.id.install_id_text)).addView(installC);
                        installC.setChecked(true);
                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).removeView(setupC);
                        setupV.addView(setup);
                        mInstallSwitcher.setText(getString(R.string.aware_install_text_done));
//                        TransitionManager.go(setup, defaultTransition);
//                        mTransitionManager.transitionTo(setup);
                        counter++;
                        break;
                    case 2:
                        findViewById(R.id.checkmark).setVisibility(View.VISIBLE);
                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
                        TransitionManager.beginDelayedTransition(counterRoot, counterTransition);
                        ViewGroup.LayoutParams params = textBackground.getLayoutParams();
                        params.width = getResources().getDimensionPixelSize(R.dimen.small_circle_size);
                        textBackground.setLayoutParams(params);

                        textProgress.setText(Html.fromHtml(""));
                        textTotal.setText("");
                        setupV.removeView(setup);
                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).addView(setupC);
                        setupC.setChecked(true);
                        lastV.addView(last);
                        mSetupSwitcher.setText(getString(R.string.aware_setup_text_done));
//                        TransitionManager.go(last, defaultTransition);
//                        mTransitionManager.transitionTo(last);
                        counter++;
                        break;
                    case 3:
                        RelativeLayout.LayoutParams theParams = (RelativeLayout.LayoutParams) textBackground.getLayoutParams();
                        theParams.height = getResources().getDimensionPixelSize(R.dimen.small_circle_size);
                        theParams.width = getResources().getDimensionPixelSize(R.dimen.normal_circle_size);
                        textBackground.setLayoutParams(theParams);

                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
                        TransitionManager.beginDelayedTransition(counterRoot, counterTransition);

                        theParams.height = getResources().getDimensionPixelSize(R.dimen.normal_circle_size);
                        textBackground.setLayoutParams(theParams);
                        findViewById(R.id.checkmark).setVisibility(View.INVISIBLE);

                        textProgress.setText("3");
                        textTotal.setText("/3");
                        setupV.addView(setup);
                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).removeView(setupC);
                        setupC.setChecked(false);
                        lastV.removeView(last);
                        mSetupSwitcher.setText(getString(R.string.aware_setup_text));
//                        TransitionManager.go(setup, defaultTransition);
//                        mTransitionManager.transitionTo(setup);
                        counter++;
                        break;
                    case 4:
                        findViewById(R.id.checkmark).setVisibility(View.VISIBLE);
                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
                        textProgress.setText("2");
                        installV.addView(install);
                        ((ViewGroup) findViewById(R.id.install_id_text)).removeView(installC);
                        installC.setChecked(false);
                        ((ViewGroup) findViewById(R.id.final_setup_ids_text)).addView(setupC);
                        setupV.removeView(setup);
                        mInstallSwitcher.setText(getString(R.string.aware_install_text));
//                        TransitionManager.go(install, defaultTransition);
//                        mTransitionManager.transitionTo(install);
                        counter++;
                        break;
                    case 5:
                        TransitionManager.beginDelayedTransition(scenesGroupRoot, defaultTransition);
                        textProgress.setText("1");
                        downloadV.addView(download);
                        ((ViewGroup) findViewById(R.id.download_id_text)).removeView(downloadC);
                        ((ViewGroup) findViewById(R.id.install_id_text)).addView(installC);
                        installV.removeView(install);
                        mDownloadSwitcher.setText(getString(R.string.aware_download_text));
//                        TransitionManager.go(download, defaultTransition);
//                        mDownloadSwitcher.setText(getString(R.string.aware_download_text));
//                        mTransitionManager.transitionTo(download);
                        counter = 0;
                        break;
                }
            }
        });
    }
}
