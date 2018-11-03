package sample.pos.ecd.co.ir.ecdpaymentsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        showResult();
    }

    private void showResult() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(Const.RESULT_KEY);

        TextView resultTxt = findViewById(R.id.resultTxt);
        resultTxt.setText(message);
    }
}
