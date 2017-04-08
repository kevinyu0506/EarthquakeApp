package tw.edu.bpmlab.mis.nccu.earthquakeapp;

import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.sql.Array;

import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;

public class settings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);



        //eqInfoButton連接到settings_eqinfo頁面

        Button eqInfoButton = (Button)findViewById(R.id.eqInfoButton);
        eqInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_eqinfo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
            }
        });



        //eqAboutButton連結到settings_about頁面

        Button eqAboutButton = (Button)findViewById(R.id.eqAboutButton);
        eqAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, settings_about.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_leftin, R.anim.slide_leftout);
            }
        });



        //alarmButton連結到alert頁面

        ImageButton alarmButton = (ImageButton)findViewById(R.id.alarmButton);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, alert.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



        //mapButton連結到map頁面

        ImageButton mapButton = (ImageButton)findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(settings.this, MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



        //紀錄viberation開關設定

        final AudioManager myAudioManager;
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final SharedPreferences viberationIsSet = getSharedPreferences("viberation",0);
        boolean viberate = viberationIsSet.getBoolean("viberation",false);
        final Switch viberationSwitch = (Switch)findViewById(R.id.viberationSwitch);
        viberationIsSet.edit().clear().commit();
        viberationSwitch.setChecked(viberate);


        viberationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                NotificationManager notificationManager =
                        (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(intent);
                }


                if (isChecked){
                    viberationIsSet.edit().clear();
                    viberationSwitch.setChecked(true);
                    viberationIsSet.edit().putBoolean("viberation",true).commit();
                    myAudioManager.setRingerMode(RINGER_MODE_NORMAL);

                }
                else {
                    viberationIsSet.edit().clear();
                    viberationSwitch.setChecked(false);
                    viberationIsSet.edit().putBoolean("viberation",false).commit();
                    myAudioManager.setRingerMode(RINGER_MODE_SILENT);

                }
            }
        });



        //ringtoneButton打開設定提示聲視窗

        Button ringtoneButton = (Button)findViewById(R.id.ringtoneButton);
        ringtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                Uri currenturi = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currenturi);
                startActivityForResult( intent, 999);

            }

        });
//        RingtoneManager.setActualDefaultRingtoneUri(settings.this, RingtoneManager.TYPE_RINGTONE, currenturi);






        final ImageButton magnitude3 = (ImageButton) findViewById(R.id.magnitude3);
        final ImageButton magnitude4 = (ImageButton) findViewById(R.id.magnitude4);
        final ImageButton magnitude5 = (ImageButton) findViewById(R.id.magnitude5);
        final ImageButton magnitude6 = (ImageButton) findViewById(R.id.magnitude6);

        final SharedPreferences magnitude= getSharedPreferences("magnitude", 0);
        int magnitudevalue = magnitude.getInt("btnChecked",0);

        switch(magnitudevalue){
            case 0:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 3:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3_picked);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 4:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4_picked);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 5:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5_picked);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                break;
            case 6:
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6_picked);
                break;

    }
        magnitude3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    magnitude.edit().clear();
                    magnitude3.setBackgroundResource(R.drawable.magnitude_3_picked);
                    magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                    magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                    magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                    magnitude.edit().putInt("btnChecked", 3).commit();
                }
            });

        magnitude4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4_picked);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                magnitude.edit().putInt("btnChecked", 4).commit();
            }
        });

        magnitude5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5_picked);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6);
                magnitude.edit().putInt("btnChecked", 5).commit();
            }
        });

        magnitude6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnitude.edit().clear();
                magnitude3.setBackgroundResource(R.drawable.magnitude_3);
                magnitude4.setBackgroundResource(R.drawable.magnitude_4);
                magnitude5.setBackgroundResource(R.drawable.magnitude_5);
                magnitude6.setBackgroundResource(R.drawable.magnitude_6_picked);
                magnitude.edit().putInt("btnChecked", 6).commit();
            }
        });



        // 讀取前面charge設定到settings頁面，以及可以在settings頁面複寫掉設定

        final SharedPreferences chargeIsSet= getSharedPreferences("charge", 0);
        boolean charge = chargeIsSet.getBoolean("charge",false);
        final Switch chargeModeSwitch = (Switch) findViewById(R.id.chargeModeSwitch);
        chargeModeSwitch.setChecked(charge);

        chargeModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    chargeIsSet.edit().clear();
                    chargeModeSwitch.setChecked(true);
                    chargeIsSet.edit().putBoolean("charge",true).commit();
                }
                else {
                    chargeIsSet.edit().clear();
                    chargeModeSwitch.setChecked(false);
                    chargeIsSet.edit().putBoolean("charge",false).commit();
                }
            }}
        );



        // 讀取前面wifi設定到settings頁面，以及可以在settings頁面複寫掉設定

        final SharedPreferences wifiIsSet= getSharedPreferences("wifi", 0);
        boolean wifi = wifiIsSet.getBoolean("wifi",false);
        final Switch wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);
        wifiSwitch.setChecked(wifi);


        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    wifiIsSet.edit().clear();
                    wifiSwitch.setChecked(true);
                    wifiIsSet.edit().putBoolean("wifi",true).commit();
                                                            }
                else {
                    wifiIsSet.edit().clear();
                    wifiSwitch.setChecked(false);
                    wifiIsSet.edit().putBoolean("wifi",false).commit();
                }
            }}
        );



        final boolean gpsCheck = isOpenGps();
        final Switch gpsSwitch = (Switch) findViewById(R.id.gpsSwitch);
        gpsSwitch.setChecked(gpsCheck);

        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Intent i = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
                else {
                    gpsSwitch.setChecked(false);
                }
            }}
        );


    }



    private boolean isOpenGps() {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通過WLAN或移動網路(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }



}















