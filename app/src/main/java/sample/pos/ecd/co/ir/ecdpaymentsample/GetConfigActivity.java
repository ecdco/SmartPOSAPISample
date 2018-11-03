package sample.pos.ecd.co.ir.ecdpaymentsample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ir.co.ecd.pos.smart.models.ConfigRequest;
import ir.co.ecd.pos.smart.models.ConfigResult;

public class GetConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_config);

        getConfig();
    }

    private void getConfig() {
        ConfigRequest configRequest = new ConfigRequest(this);
        configRequest.setPassword("123456");
        boolean result = configRequest.send();

        Log.d("ECD", "ConfigRequest result: " + result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("RequestResult");
                ConfigResult configResult = new ConfigResult(result);
                if (configResult.isOK()) {
                    String message = "\n" +
                            "isOK: " + configResult.isOK() +  "\n" +
                            "RequestStatus: " + configResult.getRequestStatus() + "\n" +
                            "MerchantNumber: " + configResult.getMerchantNumber() + "\n" +
                            "MerchantTitle: " + configResult.getMerchantTitle() + "\n" +
                            "MerchantPhoneNumber: " + configResult.getMerchantPhoneNumber() + "\n" +
                            "MerchantPostalCode: " + configResult.getMerchantPostalCode() + "\n" +
                            "TerminalNumber: " + configResult.getTerminalNumber() + "\n";

                    goResult(message);
                }
            }
        }
    }

    private void goResult(String message) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Const.RESULT_KEY, message);
        finish();
        startActivity(intent);
    }
}
