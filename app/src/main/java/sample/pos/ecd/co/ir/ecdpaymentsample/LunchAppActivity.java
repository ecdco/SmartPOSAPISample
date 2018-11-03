package sample.pos.ecd.co.ir.ecdpaymentsample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ir.co.ecd.pos.smart.models.LunchAppRequest;

public class LunchAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_app);

        lunchApp();
    }

    private void lunchApp() {
        LunchAppRequest configRequest = new LunchAppRequest(this);
        boolean result = configRequest.send();

        Log.d("ECD", "LunchAppRequest result: " + result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                // No response

            }
        }
    }
}
