package sample.pos.ecd.co.ir.ecdpaymentsample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import ir.co.ecd.pos.smart.consts.RequestMethodConst;
import ir.co.ecd.pos.smart.consts.TransactionTypeConst;
import ir.co.ecd.pos.smart.models.PaymentRequest;
import ir.co.ecd.pos.smart.models.PaymentResult;

public class DoTransactionActivity extends AppCompatActivity {


    private LinearLayout amountLayout;
    private LinearLayout saleIdLayout;
    private LinearLayout balanceLayout;
    private LinearLayout payIdLayout;
    private EditText amountTxt;
    private EditText saleIdTxt;
    private EditText payIdTxt;
    private TextView balanceLbl;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_transaction);

        initLayout();
        initSpinner();
        initButton();
        initEditText();
        initTextView();
    }

    private void initLayout() {
        amountLayout = findViewById(R.id.amountLayout);
        saleIdLayout = findViewById(R.id.saleIdLayout);
        balanceLayout = findViewById(R.id.balanceLayout);
        payIdLayout = findViewById(R.id.paymentIdLayout);
    }

    private void initSpinner() {
        final Spinner transactionDropdown = findViewById(R.id.transaction_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.do_transaction_array, android.R.layout.simple_spinner_item);
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
                doTransaction(getRequestMethod(position));
            }
        });
    }

    private void initEditText() {
        amountTxt = findViewById(R.id.amountTxt);
        saleIdTxt = findViewById(R.id.saleIdTxt);
        payIdTxt = findViewById(R.id.paymentIdTxt);
    }

    private void initTextView() {
        balanceLbl = findViewById(R.id.balanceLbl);
    }

    private void goneAllLayout() {
        amountLayout.setVisibility(View.GONE);
        saleIdLayout.setVisibility(View.GONE);
        balanceLayout.setVisibility(View.GONE);
        payIdLayout.setVisibility(View.GONE);
    }

    private void visibleSelectedLayout(int position) {
        switch (position) {
            case 0:
                amountLayout.setVisibility(View.VISIBLE);
                payIdLayout.setVisibility(View.VISIBLE);
                this.position = position;
                break;
            case 1:
                amountLayout.setVisibility(View.VISIBLE);
                saleIdLayout.setVisibility(View.VISIBLE);
                payIdLayout.setVisibility(View.VISIBLE);
                this.position = position;
                break;
            case 2:
                balanceLayout.setVisibility(View.VISIBLE);
                this.position = position;
                break;
        }
    }

    private void doTransaction(int transactionType) {
        PaymentRequest paymentRequest = new PaymentRequest(this);
        paymentRequest.setRequestMethod(RequestMethodConst.Do_Transaction);
        paymentRequest.setTransactionType(transactionType);

        switch (transactionType) {
            case 1:
                paymentRequest.setAmount(amountTxt.getText().toString());
                if (!payIdTxt.getText().toString().isEmpty())
                    paymentRequest.setPaymentId(payIdTxt.getText().toString());
                break;

            case 2:
                paymentRequest.setAmount(amountTxt.getText().toString());
                paymentRequest.setSaleId(saleIdTxt.getText().toString());
                if (!payIdTxt.getText().toString().isEmpty())
                    paymentRequest.setPaymentId(payIdTxt.getText().toString());
                break;

            case 3:
                break;

        }

        boolean result = paymentRequest.send();
    }

    private int getRequestMethod(int position) {
        int requestMethod = 0;
        switch (position) {
            case 0:
                requestMethod = TransactionTypeConst.Sale;
                break;

            case 1:
                requestMethod = TransactionTypeConst.SaleById;
                break;

            case 2:
                requestMethod = TransactionTypeConst.Balance;
                break;
        }

        return requestMethod;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("RequestResult");
                PaymentResult paymentResult = new PaymentResult(result);
                if (paymentResult.isOK()) {
                    balanceLbl.setText(paymentResult.getAmount());
                    String message = "\n" +
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
