package sample.pos.ecd.co.ir.ecdpaymentsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initBtnDoTransaction();
        initBtnGetTransaction();
        initBtnPrintTransaction();
        initBtnReportTransaction();
        initBtnGetConfig();
        initBtnLunchApp();
    }


    private void initBtnDoTransaction() {
        Button btnDoTransaction = findViewById(R.id.btnDoTransaction);
        final Intent intent = new Intent(this, DoTransactionActivity.class);
        btnDoTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initBtnGetTransaction() {
        Button btnGetTransaction = findViewById(R.id.btnGetTransaction);
        final Intent intent = new Intent(this, GetTransactionActivity.class);
        btnGetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initBtnPrintTransaction() {
        Button btnPrintTransaction = findViewById(R.id.btnPrintTransaction);
        final Intent intent = new Intent(this, PrintTransactionActivity.class);
        btnPrintTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initBtnReportTransaction() {
        Button btnReportTransaction = findViewById(R.id.btnReportTransaction);
        final Intent intent = new Intent(this, ReportTransactionActivity.class);
        btnReportTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initBtnGetConfig() {
        Button btnReportTransaction = findViewById(R.id.btnGetConfig);
        final Intent intent = new Intent(this, GetConfigActivity.class);
        btnReportTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initBtnLunchApp() {
        Button btnReportTransaction = findViewById(R.id.btnLunchApp);
        final Intent intent = new Intent(this, LunchAppActivity.class);
        btnReportTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
