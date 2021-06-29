package talant.skyqfreeremote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;
import android.widget.*;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button powerB;
    Button searchB;
    Button infoB;
    Button rewindB;
    Button playB;
    Button pauseB;
    Button stopB;
    Button recB;
    Button ffwdB;
    Button upB;
    Button downB;
    Button leftB;
    Button rightB;
    Button homeB;
    Button selectB;
    Button backupB;
    Button chup;
    Button chdown;
    Button oneB;
    Button twoB;
    Button threeB;
    Button fourB;
    Button fiveB;
    Button sixB;
    Button sevenB;
    Button eightB;
    Button nineB;
    Button zeroB;
    Button redB;
    Button greenB;
    Button yellowB;
    Button blueB;

    ConstraintLayout mScreen;


    SharedPreferences sharedprefs;
    public static String hostadd;
    public static int darklightmode = 0;
    public static String errormsg = null;
    String boxinfo = "no info found";


    public void onClick(View v) {

        if (hostadd.equals("0.0.0.0")) {
            popAlert("Please set IP address of Sky Q box in settings menu", "Host IP Invalid");

        } else {
            Button b = findViewById(v.getId());
            String btn_tag = b.getTag().toString();
            RemoteFunc.doCode(btn_tag);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreen = findViewById(R.id.mainAct);

        powerB = findViewById(R.id.powerB);
        powerB.setOnClickListener(this);

        searchB = findViewById(R.id.searchB);
        searchB.setOnClickListener(this);

        infoB = findViewById(R.id.infoB);
        infoB.setOnClickListener(this);

        homeB = findViewById(R.id.homeB);
        homeB.setOnClickListener(this);

        backupB = findViewById(R.id.backB);
        backupB.setOnClickListener(this);

        rewindB = findViewById(R.id.rewindB);
        rewindB.setOnClickListener(this);

        playB = findViewById(R.id.playB);
        playB.setOnClickListener(this);

        pauseB = findViewById(R.id.pauseB);
        pauseB.setOnClickListener(this);

        stopB = findViewById(R.id.stopB);
        stopB.setOnClickListener(this);

        recB = findViewById(R.id.recordB);
        recB.setOnClickListener(this);

        ffwdB = findViewById(R.id.fastforwardB);
        ffwdB.setOnClickListener(this);

        upB = findViewById(R.id.upB);
        upB.setOnClickListener(this);

        downB = findViewById(R.id.downB);
        downB.setOnClickListener(this);

        leftB = findViewById(R.id.leftB);
        leftB.setOnClickListener(this);

        rightB = findViewById(R.id.rightB);
        rightB.setOnClickListener(this);

        selectB = findViewById(R.id.selectB);
        selectB.setOnClickListener(this);

        chup = findViewById(R.id.chupB);
        chup.setOnClickListener(this);

        chdown = findViewById(R.id.chdownB);
        chdown.setOnClickListener(this);

        oneB = findViewById(R.id.oneB);
        oneB.setOnClickListener(this);

        twoB = findViewById(R.id.twoB);
        twoB.setOnClickListener(this);

        threeB = findViewById(R.id.threeB);
        threeB.setOnClickListener(this);

        fourB = findViewById(R.id.fourB);
        fourB.setOnClickListener(this);

        fiveB = findViewById(R.id.fiveB);
        fiveB.setOnClickListener(this);

        sixB = findViewById(R.id.sixB);
        sixB.setOnClickListener(this);

        sevenB = findViewById(R.id.sevenB);
        sevenB.setOnClickListener(this);

        eightB = findViewById(R.id.eightB);
        eightB.setOnClickListener(this);

        nineB = findViewById(R.id.nineB);
        nineB.setOnClickListener(this);

        zeroB = findViewById(R.id.zeroB);
        zeroB.setOnClickListener(this);

        redB = findViewById(R.id.redB);
        redB.setOnClickListener(this);

        greenB = findViewById(R.id.greenB);
        greenB.setOnClickListener(this);

        yellowB = findViewById(R.id.yellowB);
        yellowB.setOnClickListener(this);

        blueB = findViewById(R.id.blueB);
        blueB.setOnClickListener(this);

        sharedprefs = getSharedPreferences("skyqfreeremote", Context.MODE_PRIVATE);
        hostadd = sharedprefs.getString("hostadd", "0.0.0.0");
        darklightmode = sharedprefs.getInt("darklight", 0);

        if (darklightmode == 1) {
            mScreen.setBackgroundColor(Color.BLACK);
        }



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.aboutButt:
                String versionName = BuildConfig.VERSION_NAME;
                String msgBody = "Sky Q Free Remote v" + versionName + " by Talant Apps\ntalantapps@gmail.com";
                popAlert(msgBody, "About");
                return true;

            case R.id.darkModeButt:
                int colorId = Color.WHITE;
                SharedPreferences sharedprefs = getSharedPreferences("skyqfreeremote", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedprefs.edit();

                try {
                    ColorDrawable viewColor = (ColorDrawable) mScreen.getBackground();
                    colorId = viewColor.getColor();
                }
                catch (Exception e){}

                if (colorId == Color.WHITE) {
                    mScreen.setBackgroundColor(Color.BLACK);
                    editor.putInt("darklight", 1);
                    editor.commit();
                }
                else
                {
                    mScreen.setBackgroundColor(Color.WHITE);
                    editor.putInt("darklight", 0);
                    editor.commit();
                }
                return true;

            case R.id.boxInfoButt:
                if (hostadd.equals("0.0.0.0")) {
                    popAlert("Please set IP address of Sky Q box in settings menu", "Host IP Invalid");
                } else {
                    doBoxInfoinBG();
                }
                return true;

            case R.id.settingButt:
                Intent myIntent = new Intent(MainActivity.this,
                        SettingsActivity.class);
                startActivity(myIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void popAlert(String body, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(body);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private static String getBoxInfo() throws Exception {
        String USER_AGENT = "Mozilla/5.0";

        URL theurl = new URL("http", hostadd, 9006, "/as/system/information");

        HttpURLConnection con = (HttpURLConnection) theurl.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setConnectTimeout(3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }


    private void doBoxInfoinBG() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    boxinfo = getBoxInfo();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popAlert(boxinfo, "Sky Q Box Info");
                    }
                });
            }
        };
        thread.start();

    }
}