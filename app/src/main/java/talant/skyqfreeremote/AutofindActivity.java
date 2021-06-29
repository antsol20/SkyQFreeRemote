package talant.skyqfreeremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import  androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class AutofindActivity extends AppCompatActivity {

    TextView outputtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autofind);
        outputtv = findViewById(R.id.outputtv);
        outputtv.setMovementMethod(new ScrollingMovementMethod());
        String subnetrange = getsubnetrange();
        appendResultsText("Starting to search local network..." + subnetrange + ".0-255");
        if (subnetrange.equals("0.0.0")){
            appendResultsText("IS YOUR WIFI TURNED ON  !!! ??????");
        }
        doPortScan(subnetrange);
    }

    private void appendResultsText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String currentText = outputtv.getText().toString();
                outputtv.setText(currentText + "\n" + text);
            }
        });
    }

    private String getsubnetrange() {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wm.getConnectionInfo().getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        String[] x = ip.split("[.]");
        String ipSub = x[0] + "." + x[1] + "." + x[2];
        return ipSub;
    }


    private boolean scanP(String ip) {

        Socket s = null;
        try {
            s = new Socket();
            s.connect(new InetSocketAddress(ip, RemoteFunc.sPort),1000);
            return true;
        } catch (IOException e) {
            // Don't log anything as we are expecting a lot of these from closed ports.
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;


    }

    private void doPortScan(final String s) {

        Thread bgThread = new Thread() {
            public void run() {
                try {
                    for (int i = 0; i < 256; i++) {
                        String iptoscan = s + "." + i;
                        appendResultsText("Scanning..." + iptoscan);
                        if(scanP(iptoscan))
                        {
                            appendResultsText("Found open port on " + iptoscan);
                            appendResultsText("Possible SkyQ Box, saving IP address");
                            MainActivity.hostadd = iptoscan;
                            SharedPreferences sharedprefs = getSharedPreferences("skyqfreeremote", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedprefs.edit();
                            editor.putString("hostadd", MainActivity.hostadd);
                            editor.commit();
                            appendResultsText("Finished");
                            return;
                        }

                    }
                } catch (Exception e) {
                    appendResultsText(e.toString());
                }
            }
        };
        bgThread.start();


    }
}
