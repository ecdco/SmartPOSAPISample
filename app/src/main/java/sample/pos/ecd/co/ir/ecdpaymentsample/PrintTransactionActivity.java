package sample.pos.ecd.co.ir.ecdpaymentsample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import ir.co.ecd.pos.smart.consts.RequestMethodConst;
import ir.co.ecd.pos.smart.models.PrintRequest;
import ir.co.ecd.pos.smart.models.PrintResult;

public class PrintTransactionActivity extends AppCompatActivity {

    private LinearLayout referenceNumberLayout;
    private LinearLayout trackingNumberLayout;
    private LinearLayout paymentIdLayout;

    private EditText referenceNumberTxt;
    private EditText trackingNumberTxt;
    private EditText paymentIdTxt;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_transaction);

        initLayouts();
        initEditText();
        initSpinner();
        initButton();
    }
    private void initLayouts() {
        referenceNumberLayout = findViewById(R.id.referenceNumberLayout);
        trackingNumberLayout = findViewById(R.id.trackingNumberLayout);
        paymentIdLayout = findViewById(R.id.paymentIdLayout);
    }

    private void initSpinner() {
        final Spinner transactionDropdown = findViewById(R.id.transaction_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.get_transaction_array,  android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionDropdown.setAdapter(adapter);
        transactionDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                goneAllLayout();
                visibleSelectedLayout(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void initEditText(){
        referenceNumberTxt = findViewById(R.id.referenceNumberTxt);
        trackingNumberTxt = findViewById(R.id.trackingNumberTxt);
        paymentIdTxt = findViewById(R.id.paymentIdTxt);
    }

    private void initButton() {
        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printTransaction(getRequestMethod(position));
            }
        });
    }


    private void goneAllLayout() {
        referenceNumberLayout.setVisibility(View.GONE);
        trackingNumberLayout.setVisibility(View.GONE);
        paymentIdLayout.setVisibility(View.GONE);
    }

    private void visibleSelectedLayout(int position) {
        switch (position) {
            case 0:
                referenceNumberLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Print_Transaction_By_ReferenceNumber;
                break;
            case 1:
                trackingNumberLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Print_Transaction_By_TrackingNumber;
                break;
            case 2:
                paymentIdLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Print_Transaction_By_PaymentId;
                break;
        }
    }

    private int getRequestMethod(int position) {
        int requestMethod = 0;
        switch (position) {
            case RequestMethodConst.Print_Transaction_By_ReferenceNumber:
                requestMethod = RequestMethodConst.Print_Transaction_By_ReferenceNumber;
                break;

            case RequestMethodConst.Print_Transaction_By_TrackingNumber:
                requestMethod = RequestMethodConst.Print_Transaction_By_TrackingNumber;
                break;

            case RequestMethodConst.Print_Transaction_By_PaymentId:
                requestMethod = RequestMethodConst.Print_Transaction_By_PaymentId;
                break;
        }

        return requestMethod;
    }

    private void printTransaction(int methodType) {
        PrintRequest printRequest = new PrintRequest(this);
        printRequest.setRequestMethod(methodType);
        switch (methodType) {
            case RequestMethodConst.Print_Transaction_By_ReferenceNumber:
                printRequest.setReferenceNumber(referenceNumberTxt.getText().toString());
                break;

            case RequestMethodConst.Print_Transaction_By_TrackingNumber:
                printRequest.setTrackingNumber(trackingNumberTxt.getText().toString());
                break;

            case RequestMethodConst.Print_Transaction_By_PaymentId:
                printRequest.setPaymentId(paymentIdTxt.getText().toString());
                break;

        }

        boolean result = printRequest.send();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("RequestResult");
                PrintResult printResult = new PrintResult(result);
                if (printResult.isOK()) {

                }
            }
        }
    }



}
