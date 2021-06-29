package talant.skyqfreeremote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity  {

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tv = findViewById(R.id.hostET);
        tv.setText(MainActivity.hostadd);



        findViewById(R.id.findButt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent myIntent = new Intent(SettingsActivity.this,
                            AutofindActivity.class);
                    startActivity(myIntent);

                }
            }
        });

        findViewById(R.id.saveButt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hostadd = tv.getText().toString();
                SharedPreferences sharedprefs = getSharedPreferences("skyqfreeremote", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedprefs.edit();
                editor.putString("hostadd", MainActivity.hostadd);
                editor.commit();

                Toast toast = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        tv.setText(MainActivity.hostadd);

    }


}
