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
import ir.co.ecd.pos.smart.models.PaymentRequest;
import ir.co.ecd.pos.smart.models.PaymentResult;

public class GetTransactionActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_get_transaction);

        initLayouts();
        initSpinner();
        initEditText();
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

    private void initButton() {
        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTransaction(getRequestMethod(position));
            }
        });
    }

    private void initEditText(){
        referenceNumberTxt = findViewById(R.id.referenceNumberTxt);
        trackingNumberTxt = findViewById(R.id.trackingNumberTxt);
        paymentIdTxt = findViewById(R.id.paymentIdTxt);
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
                this.position = RequestMethodConst.Get_Transaction_By_ReferenceNumber;
                break;
            case 1:
                trackingNumberLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Get_Transaction_By_TrackingNumber;
                break;
            case 2:
                paymentIdLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Get_Transaction_By_PaymentId;
                break;
        }
    }

    private int getRequestMethod(int position) {
        int requestMethod = 0;
        switch (position) {
            case RequestMethodConst.Get_Transaction_By_ReferenceNumber:
                requestMethod = RequestMethodConst.Get_Transaction_By_ReferenceNumber;
                break;

            case RequestMethodConst.Get_Transaction_By_TrackingNumber:
                requestMethod = RequestMethodConst.Get_Transaction_By_TrackingNumber;
                break;

            case RequestMethodConst.Get_Transaction_By_PaymentId:
                requestMethod = RequestMethodConst.Get_Transaction_By_PaymentId;
                break;
        }

        return requestMethod;
    }

    private void getTransaction(int methodType) {
        PaymentRequest paymentRequest = new PaymentRequest(this);
        paymentRequest.setRequestMethod(methodType);
        switch (methodType) {
            case RequestMethodConst.Get_Transaction_By_ReferenceNumber:
                paymentRequest.setReferenceNumber(referenceNumberTxt.getText().toString());
                break;

            case RequestMethodConst.Get_Transaction_By_TrackingNumber:
                paymentRequest.setTrackingNumber(trackingNumberTxt.getText().toString());
                break;

            case RequestMethodConst.Get_Transaction_By_PaymentId:
                paymentRequest.setPaymentId(paymentIdTxt.getText().toString());
                break;

        }

        boolean result = paymentRequest.send();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("RequestResult");
                PaymentResult paymentResult = new PaymentResult(result);
                if (paymentResult.isOK()) {

                    String message ="\n" +
                            "isOK: " + paymentResult.isOK() +  "\n" +
                            "RequestStatus: " + paymentResult.getRequestStatus() + "\n" +
                            "TransactionType: " + paymentResult.getTransactionType() + "\n" +
                            "TransactionStatus: " + paymentResult.getTransactionStatus() + "\n" +
                            "ErrorCode: " + paymentResult.getErrorCode() + "\n" +
                            "transactionIsSuccessful: " + paymentResult.transactionIsSuccessful() + "\n" +
                            "transactionHasUserError: " + paymentResult.transactionHasUserError() + "\n" +
                            "PaymentId: " + paymentResult.getPaymentId() + "\n" +
                            "CardNumber: " + paymentResult.getCardNumber() + "\n" +
                            "BankName: " + paymentResult.getBankName() + "\n" +
                            "Amount: " + paymentResult.getAmount() + "\n" +
                            "ReferenceNumber: " + paymentResult.getReferenceNumber() + "\n" +
                            "TrackingNumber: " + paymentResult.getTrackingNumber() + "\n" +
                            "Date: " + paymentResult.getDate() + "\n" +
                            "Time: " + paymentResult.getTime() + "\n" +
                            "DateFormatted: " + paymentResult.getDateFormatted() + "\n" +
                            "TimeFormatted: " + paymentResult.getTimeFormatted() + "\n" +
                            "CreatedTime: " + paymentResult.getCreatedTime() + "\n" +
                            "MerchantNumber: " + paymentResult.getMerchantNumber() + "\n" +
                            "TerminalNumber: " + paymentResult.getTerminalNumber() + "\n" +
                            "SerialNumber: " + paymentResult.getSerialNumber() + "\n" +
                            "isSaleTransaction: " + paymentResult.isSaleTransaction() + "\n" +
                            "isBalanceTransaction: " + paymentResult.isBalanceTransaction() + "\n" +
                            "isSaleByIdTransaction: " + paymentResult.isSaleByIdTransaction() + "\n" +
                            "getSaleId: " + paymentResult.getSaleId() + "\n" +
                            "getDescription: " + paymentResult.getDescription() ;

                    goResult(message);

                }
            }
        }
    }

    private void goResult(String message) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Const.RESULT_KEY, message);
        startActivity(intent);
    }
}
