package com.polybiastudios.photosweep;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

public class MainActivity extends ActionBarActivity {
    private static MainActivity mainActivity = null;

    SharedPreferences settings = null;

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private DashboardFragment dashboardFragment;
    private PhotoDetailsFragment photoDetailsFragment;
    private PrintQueueFragment printQueueFragment;
    private SettingsFragment settingsFragment;
    private CreditCardFragment creditCardFragment;
    private CcvFragment ccvFragment;
    private InfoFragment infoFragment;
    private FaqFragment faqFragment;
    private PricingFragment pricingFragment;

    private ImageView blurImageView;

    private LinearLayout dashboardLayout;
    private LinearLayout printQueueLayout;
    private LinearLayout settingsLayout;
    private LinearLayout infoLayout;

    private ImageButton dashboardButton;
    private ImageButton printQueueButton;
    private ImageButton settingsButton;
    private ImageButton infoButton;

    private TextView dashboardLabel;
    private TextView printQueueLabel;
    private TextView settingsLabel;
    private TextView infoLabel;

    private static ActionBar actionBar;
    private static FragmentManager fragmentManager;
    private static android.app.FragmentManager dialogFragmentManager;
    private MultiStateToggleButton toggleMenu;

    public static MainActivity getInstance(){
        if(mainActivity == null){
            mainActivity = new MainActivity();
        }
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blurImageView = (ImageView)findViewById(R.id.blur_image_view);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);

            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        fragmentManager = getSupportFragmentManager();
        dialogFragmentManager = getFragmentManager();

        dashboardLayout = (LinearLayout)findViewById(R.id.dashboard_layout);
        printQueueLayout = (LinearLayout)findViewById(R.id.print_queue_layout);
        settingsLayout = (LinearLayout)findViewById(R.id.settings_layout);
        infoLayout = (LinearLayout)findViewById(R.id.info_layout);

        dashboardButton = (ImageButton)findViewById(R.id.dashboard_button);
        printQueueButton = (ImageButton)findViewById(R.id.print_queue_button);
        settingsButton = (ImageButton)findViewById(R.id.settings_button);
        infoButton = (ImageButton)findViewById(R.id.info_button);

        dashboardLabel = (TextView)findViewById(R.id.dashboard_label);
        printQueueLabel = (TextView)findViewById(R.id.print_queue_label);
        settingsLabel = (TextView)findViewById(R.id.settings_label);
        infoLabel = (TextView)findViewById(R.id.info_label);

        dashboardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard();
                changeActiveIcon(0);
            }
        });
        printQueueLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPrintQueue();
                changeActiveIcon(1);
            }
        });
        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
                changeActiveIcon(2);
            }
        });
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInfo();
                changeActiveIcon(3);
            }
        });

        openPhotoDetails();
    }

    public void changeActiveIcon(int i){
        ColorFilter greenFilter = new PorterDuffColorFilter(getResources().getColor(R.color.ps_green_dk), PorterDuff.Mode.SRC_ATOP);
        ColorFilter greyFilter = new PorterDuffColorFilter(getResources().getColor(R.color.ps_grey), PorterDuff.Mode.SRC_ATOP);
        int green = getResources().getColor(R.color.ps_green_dk);
        int grey = getResources().getColor(R.color.ps_grey);
        switch (i) {
            case 0:
                dashboardButton.setColorFilter(greenFilter);
                printQueueButton.setColorFilter(greyFilter);
                settingsButton.setColorFilter(greyFilter);
                infoButton.setColorFilter(greyFilter);

                dashboardLabel.setTextColor(green);
                printQueueLabel.setTextColor(grey);
                settingsLabel.setTextColor(grey);
                infoLabel.setTextColor(grey);
                return;
            case 1:
                dashboardButton.setColorFilter(greyFilter);
                printQueueButton.setColorFilter(greenFilter);
                settingsButton.setColorFilter(greyFilter);
                infoButton.setColorFilter(greyFilter);

                dashboardLabel.setTextColor(grey);
                printQueueLabel.setTextColor(green);
                settingsLabel.setTextColor(grey);
                infoLabel.setTextColor(grey);
                return;
            case 2:
                dashboardButton.setColorFilter(greyFilter);
                printQueueButton.setColorFilter(greyFilter);
                settingsButton.setColorFilter(greenFilter);
                infoButton.setColorFilter(greyFilter);

                dashboardLabel.setTextColor(grey);
                printQueueLabel.setTextColor(grey);
                settingsLabel.setTextColor(green);
                infoLabel.setTextColor(grey);
                return;
            case 3:
                dashboardButton.setColorFilter(greyFilter);
                printQueueButton.setColorFilter(greyFilter);
                settingsButton.setColorFilter(greyFilter);
                infoButton.setColorFilter(greenFilter);

                dashboardLabel.setTextColor(grey);
                printQueueLabel.setTextColor(grey);
                settingsLabel.setTextColor(grey);
                infoLabel.setTextColor(green);
                return;
            default:
                dashboardButton.setColorFilter(greyFilter);
                printQueueButton.setColorFilter(greyFilter);
                settingsButton.setColorFilter(greyFilter);
                infoButton.setColorFilter(greyFilter);

                dashboardLabel.setTextColor(grey);
                printQueueLabel.setTextColor(grey);
                settingsLabel.setTextColor(grey);
                infoLabel.setTextColor(grey);
                break;
        }
    }

    public void toggleBackButtonEnabled(boolean enabled){
        actionBar.setDisplayHomeAsUpEnabled(enabled);
    }

    public void openLogin() {
        loginFragment = new LoginFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, loginFragment)
                .commit();
    }

    public void openRegister() {
        registerFragment = new RegisterFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, registerFragment)
                .commit();
    }

    public void openDashboard(){
        dashboardFragment = new DashboardFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, dashboardFragment)
                .commit();
    }

    public void openPhotoDetails(){
        photoDetailsFragment = new PhotoDetailsFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, photoDetailsFragment)
                .commit();
    }

    public void openPrintQueue(){
        printQueueFragment = new PrintQueueFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, printQueueFragment)
                .commit();
    }

    public void openSettings(){
        settingsFragment = new SettingsFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, settingsFragment)
                .commit();

    }

    public void openCreditCard() {
        creditCardFragment = new CreditCardFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, creditCardFragment)
                .addToBackStack("fragBack")
                .commit();
    }

    public void openCcv() {
        ccvFragment = new CcvFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, ccvFragment)
                .addToBackStack("fragBack")
                .commit();
    }

    public void openInfo(){
        infoFragment = new InfoFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, infoFragment)
                .commit();
    }

    public void openFaq(){
        faqFragment = new FaqFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, faqFragment)
                .addToBackStack("fragBack")
                .commit();

    }

    public void openPricing(){
        pricingFragment = new PricingFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, pricingFragment)
                .addToBackStack("fragBack")
                .commit();

    }
}
