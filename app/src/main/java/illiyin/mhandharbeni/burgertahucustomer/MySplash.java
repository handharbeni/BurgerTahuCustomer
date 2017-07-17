package illiyin.mhandharbeni.burgertahucustomer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by root on 17/07/17.
 */

public class MySplash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MySplash.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
