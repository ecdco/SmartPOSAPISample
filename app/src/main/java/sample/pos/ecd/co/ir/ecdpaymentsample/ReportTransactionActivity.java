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
import ir.co.ecd.pos.smart.consts.RequestMethodConst;
import ir.co.ecd.pos.smart.models.ReportRequest;
import ir.co.ecd.pos.smart.models.ReportResult;

public class ReportTransactionActivity extends AppCompatActivity {

    private LinearLayout oneDayLayout;
    private LinearLayout rangeDateLayout;

    private EditText oneDayTxt;
    private EditText fromDayTxt;
    private EditText toDayTxt;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_transaction);

        initLayouts();
        initEditText();
        initSpinner();
        initButton();

    }

    private void initLayouts() {
        oneDayLayout = findViewById(R.id.oneDayLayout);
        rangeDateLayout = findViewById(R.id.rangeDateLayout);
    }

    private void initEditText() {
        oneDayTxt = findViewById(R.id.oneDayTxt);
        fromDayTxt = findViewById(R.id.fromDayTxt);
        toDayTxt = findViewById(R.id.toDayTxt);
    }

    private void initSpinner() {
        final Spinner transactionDropdown = findViewById(R.id.transaction_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.print_transaction_array,  android.R.layout.simple_spinner_item);
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
                reportTransaction(getRequestMethod(position));
            }
        });
    }

    private void goneAllLayout() {
        oneDayLayout.setVisibility(View.GONE);
        rangeDateLayout.setVisibility(View.GONE);
    }

    private void visibleSelectedLayout(int position) {
        switch (position) {
            case 0:
                oneDayLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Get_Report_Specific_Day;
                break;
            case 1:
                rangeDateLayout.setVisibility(View.VISIBLE);
                this.position = RequestMethodConst.Get_Report_Date_Range;
                break;

        }
    }

    private int getRequestMethod(int position) {
        int requestMethod = 0;
        switch (position) {
            case RequestMethodConst.Get_Report_Specific_Day:
                requestMethod = RequestMethodConst.Get_Report_Specific_Day;
                break;

            case RequestMethodConst.Get_Report_Date_Range:
                requestMethod = RequestMethodConst.Get_Report_Date_Range;
                break;

        }

        return requestMethod;
    }

    private void reportTransaction(int methodType) {
        ReportRequest reportRequest = new ReportRequest(this);
        reportRequest.setRequestMethod(methodType);

        switch (methodType) {
            case RequestMethodConst.Get_Report_Specific_Day:
                Log.d("SINA", "Date = " + oneDayTxt.getText().toString());
                reportRequest.setDay(oneDayTxt.getText().toString());
                break;

            case RequestMethodConst.Get_Report_Date_Range:
                Log.d("SINA", "From Date = " + fromDayTxt.getText().toString());
                Log.d("SINA", "To Date = " + toDayTxt.getText().toString());

                reportRequest.setFrom(fromDayTxt.getText().toString());
                reportRequest.setTo(toDayTxt.getText().toString());
                break;

        }

        boolean result = reportRequest.send();
        Log.d("SINA", "Result = " + result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("RequestResult");
                Log.d("SINA", "Request Result = " + result);
                ReportResult reportResult = new ReportResult(result);
                if (reportResult.isOK()) {

                    String message = "\n" +
                            "Total: " + reportResult.getTotal() + "\n" +
                            "Total Success: " + reportResult.getTotalSuccess() + "\n" +
                            "Total Amount: " + reportResult.getTotalAmount();

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
